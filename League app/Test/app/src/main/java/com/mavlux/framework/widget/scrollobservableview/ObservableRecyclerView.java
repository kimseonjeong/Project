package com.mavlux.framework.widget.scrollobservableview;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;

import com.mavlux.framework.widget.adapter.OnScrollDelegate;


public class ObservableRecyclerView extends RecyclerView {

	private SparseIntArray mChildrenHeights;
	private int mPrevFirstVisiblePosition;
	private int mPrevFirstVisibleChildHeight = -1;
	private int mPrevScrolledChildrenHeight;

	private int scrollY;

	private float previousTouchMoveY;
	private ScrollState scrollState;

	private OnScrollDelegate onScrollDelegate;
	private OnTouchScrollListener onTouchScrollListener;
	private OnScrollChangedListener onScrollChangedListener;

	public ObservableRecyclerView( Context context ) {

		this( context, null );
	}

	public ObservableRecyclerView( Context context, AttributeSet attrs ) {

		super( context, attrs );
		initView();
	}

	private void initView() {

		mChildrenHeights = new SparseIntArray();
		setOnScrollListener( new OnScrollListener() {

			@Override
			public void onScrollStateChanged( RecyclerView recyclerView, int newState ) {

				if ( null != onScrollDelegate )
					onScrollDelegate.onScrollStateChanged( recyclerView, newState );
			}

			@Override
			public void onScrolled( RecyclerView recyclerView, int dx, int dy ) {

				if ( null != onScrollDelegate )
					onScrollDelegate.onScrolled( recyclerView, dx, dy );
			}

		} );
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

		this.previousTouchMoveY = touchDownY;
		this.scrollState = ScrollState.STOP;

		updateScrollY();

		if ( null != onTouchScrollListener )
			onTouchScrollListener.onTouchDown( scrollY );
	}

	private boolean touchMove( float touchMoveY ) {

		float touchDeltaY = touchMoveY - previousTouchMoveY;
		previousTouchMoveY = touchMoveY;
		scrollState = ScrollState.getScrollState(touchDeltaY);
		boolean result = false;

		if ( null != onTouchScrollListener ) {

			if ( ScrollState.UP == scrollState )
				result = onTouchScrollListener.onScrollUp( touchDeltaY, scrollY );
			else if ( ScrollState.DOWN == scrollState )
				result = onTouchScrollListener.onScrollDown( touchDeltaY, scrollY );
			else
				result = onTouchScrollListener.onScrollStop( scrollY );
		}

		return result;
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

	private void updateScrollY() {

		if ( getChildCount() > 0 ) {

			int firstVisiblePosition = getChildPosition( getChildAt( 0 ) );
			int lastVisiblePosition = getChildPosition( getChildAt( getChildCount() - 1 ) );

			for ( int i = firstVisiblePosition, j = 0; i <= lastVisiblePosition; i++, j++ ) {

				if ( mChildrenHeights.indexOfKey( i ) < 0 || getChildAt( j ).getHeight() != mChildrenHeights.get( i ) )
					mChildrenHeights.put( i, getChildAt( j ).getHeight() );
			}

			View firstVisibleChild = getChildAt( 0 );
			if ( firstVisibleChild != null ) {

				if ( mPrevFirstVisiblePosition < firstVisiblePosition ) {

					int skippedChildrenHeight = 0;
					if ( firstVisiblePosition - mPrevFirstVisiblePosition != 1 ) {

						for ( int i = firstVisiblePosition - 1; i > mPrevFirstVisiblePosition; i-- ) {

							if ( 0 < mChildrenHeights.indexOfKey( i ) )
								skippedChildrenHeight += mChildrenHeights.get( i );
							else
								skippedChildrenHeight += firstVisibleChild.getHeight();
						}
					}

					mPrevScrolledChildrenHeight += mPrevFirstVisibleChildHeight + skippedChildrenHeight;
					mPrevFirstVisibleChildHeight = firstVisibleChild.getHeight();
				}
				else if ( firstVisiblePosition < mPrevFirstVisiblePosition ) {

					int skippedChildrenHeight = 0;
					if ( mPrevFirstVisiblePosition - firstVisiblePosition != 1 ) {

						for ( int i = mPrevFirstVisiblePosition - 1; i > firstVisiblePosition; i-- ) {

							if ( 0 < mChildrenHeights.indexOfKey( i ) )
								skippedChildrenHeight += mChildrenHeights.get( i );
							else
								skippedChildrenHeight += firstVisibleChild.getHeight();
						}
					}

					mPrevScrolledChildrenHeight -= firstVisibleChild.getHeight() + skippedChildrenHeight;
					mPrevFirstVisibleChildHeight = firstVisibleChild.getHeight();
				}
				else if ( firstVisiblePosition == 0 ) {

					mPrevFirstVisibleChildHeight = firstVisibleChild.getHeight();
				}

				if ( mPrevFirstVisibleChildHeight < 0 ) {

					mPrevFirstVisibleChildHeight = 0;
				}

				scrollY = mPrevScrolledChildrenHeight - firstVisibleChild.getTop();
				scrollY = Math.max(scrollY, 0);
				mPrevFirstVisiblePosition = firstVisiblePosition;

				if ( null != onScrollChangedListener )
					onScrollChangedListener.onScrollChanged( scrollY );
			}
		}
	}

	@Override
	protected void onScrollChanged( int l, int t, int oldl, int oldt ) {

		updateScrollY();
		super.onScrollChanged( l, t, oldl, oldt );
	}

	public void setOnScrollDelegate( OnScrollDelegate onScrollDelegate ) {

		this.onScrollDelegate = onScrollDelegate;
	}

	public void setOnTouchScrollListener( OnTouchScrollListener onTouchScrollListener ) {

		this.onTouchScrollListener = onTouchScrollListener;
	}

	public void setOnScrollChangedListener( OnScrollChangedListener onScrollChangedListener ) {

		this.onScrollChangedListener = onScrollChangedListener;
	}

	public interface OnScrollChangedListener {

		void onScrollChanged(int scrollY);

	}

}
