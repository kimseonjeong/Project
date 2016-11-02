package com.mavlux.framework.widget.scrollobservableview;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.mavlux.framework.widget.adapter.OnScrollDelegate;


public class ObservableListView extends ListView implements AbsListView.OnScrollListener {

	private SparseIntArray childrenHeights;
	private int prevFirstVisiblePosition;
	private int prevFirstVisibleChildHeight = -1;
	private int prevScrolledChildrenHeight;
	private int scrollY;
	private boolean firstScroll;

	private float previousTouchMoveY;
	private ScrollState scrollState;

	private OnScrollDelegate onScrollDelegate;
	private OnTouchScrollListener onTouchScrollListener;

	public ObservableListView( Context context ) {

		this( context, null );
	}

	public ObservableListView( Context context, AttributeSet attrs ) {

		super( context, attrs );
		initView();
	}

	private void initView() {

		childrenHeights = new SparseIntArray();
		super.setOnScrollListener( this );
	}

	@Override
	public void onScrollStateChanged( AbsListView view, int scrollState ) {

		if ( null != onScrollDelegate )
			onScrollDelegate.onScrollStateChanged( view, scrollState );
	}

	@Override
	public void onScroll( AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount ) {

		onScrollChanged();

		if ( null != onScrollDelegate )
			onScrollDelegate.onScroll( view, firstVisibleItem, visibleItemCount, totalItemCount );
	}

	private void onScrollChanged() {

		if ( 0 < getChildCount() ) {

			int firstVisiblePosition = getFirstVisiblePosition();
			for ( int i = getFirstVisiblePosition(), j = 0; i <= getLastVisiblePosition(); i++, j++ ) {

				if ( childrenHeights.indexOfKey( i ) < 0 || getChildAt( j ).getHeight() != childrenHeights.get( i ) )
					childrenHeights.put( i, getChildAt( j ).getHeight() );
			}

			View firstVisibleChild = getChildAt( 0 );
			if ( null != firstVisibleChild ) {

				if ( prevFirstVisiblePosition < firstVisiblePosition ) {

					int skippedChildrenHeight = 0;
					if ( 1 != firstVisiblePosition - prevFirstVisiblePosition ) {

						for ( int i = firstVisiblePosition - 1; i > prevFirstVisiblePosition; i-- ) {

							if ( 0 < childrenHeights.indexOfKey( i ) )
								skippedChildrenHeight += childrenHeights.get( i );
							else
								skippedChildrenHeight += firstVisibleChild.getHeight();
						}
					}

					prevScrolledChildrenHeight += prevFirstVisibleChildHeight + skippedChildrenHeight;
					prevFirstVisibleChildHeight = firstVisibleChild.getHeight();
				}
				else if ( firstVisiblePosition < prevFirstVisiblePosition ) {

					int skippedChildrenHeight = 0;
					if ( prevFirstVisiblePosition - firstVisiblePosition != 1 ) {

						for ( int i = prevFirstVisiblePosition - 1; i > firstVisiblePosition; i-- ) {

							if ( 0 < childrenHeights.indexOfKey( i ) )
								skippedChildrenHeight += childrenHeights.get( i );
							else
								skippedChildrenHeight += firstVisibleChild.getHeight();
						}
					}

					prevScrolledChildrenHeight -= firstVisibleChild.getHeight() + skippedChildrenHeight;
					prevFirstVisibleChildHeight = firstVisibleChild.getHeight();
				}
				else if ( 0 == firstVisiblePosition ) {

					prevFirstVisibleChildHeight = firstVisibleChild.getHeight();
				}

				if ( 0 > prevFirstVisibleChildHeight )
					prevFirstVisibleChildHeight = 0;

				scrollY = prevScrolledChildrenHeight - firstVisibleChild.getTop();
				prevFirstVisiblePosition = firstVisiblePosition;

				if ( firstScroll )
					firstScroll = false;
			}
			else {

				Log.d("ObservableListView", "first: " + firstVisiblePosition);
			}
		}
	}

	@Override
	public boolean onInterceptTouchEvent( @NonNull MotionEvent ev ) {

		switch ( ev.getAction() ) {

			case MotionEvent.ACTION_DOWN:

				touchDown( ev.getRawY() );
				break;

			case MotionEvent.ACTION_MOVE:

				if ( touchMove( ev.getRawY() ) )
					return true;
				else
					break;
		}

		return super.onInterceptTouchEvent( ev );
	}

	@Override
	public boolean onTouchEvent( @NonNull MotionEvent ev ) {

		switch ( ev.getAction() ) {

			case MotionEvent.ACTION_MOVE:

				if ( touchMove( ev.getRawY() ) )
					return true;
				else
					break;

			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:

				if ( touchUp( ev.getRawY() ) )
					return true;
				else
					break;
		}

		return super.onTouchEvent( ev );
	}

	private void touchDown( float touchDownY ) {

		this.firstScroll = true;
		this.previousTouchMoveY = touchDownY;
		this.scrollState = ScrollState.STOP;

		if ( null != onTouchScrollListener )
			onTouchScrollListener.onTouchDown( scrollY );
	}

	private boolean touchMove( float touchMoveY ) {

		float touchDeltaY = touchMoveY - previousTouchMoveY;
		previousTouchMoveY = touchMoveY;
		scrollState = ScrollState.getScrollState( touchDeltaY );

		if ( null != onTouchScrollListener ) {

			if ( ScrollState.UP == scrollState )
				return onTouchScrollListener.onScrollUp( touchDeltaY, scrollY );
			else if ( ScrollState.DOWN == scrollState )
				return onTouchScrollListener.onScrollDown( touchDeltaY, scrollY );
			else
				return onTouchScrollListener.onScrollStop( scrollY );
		}
		else {

			return false;
		}
	}

	private boolean touchUp( float touchUpY ) {

		float touchDeltaY = previousTouchMoveY - touchUpY;
		previousTouchMoveY = touchUpY;
		this.scrollState = ScrollState.STOP;

		if ( null != onTouchScrollListener )
			return onTouchScrollListener.onTouchUp( touchDeltaY, scrollY );
		else
			return false;
	}

	public void setOnScrollDelegate( OnScrollDelegate onScrollDelegate ) {

		this.onScrollDelegate = onScrollDelegate;
	}

	public void setOnTouchScrollListener( OnTouchScrollListener onTouchScrollListener ) {

		this.onTouchScrollListener = onTouchScrollListener;
	}

}
