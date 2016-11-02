package com.mavlux.framework.toolbar;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.mavlux.test.R;
import com.nineoldandroids.view.ViewHelper;


public class ToolbarLayout extends FrameLayout {

	private View rootView;
	private Toolbar toolbar;
	private View view_shadow;
	private FrameLayout toolbar_frameLayout_content;

	public ToolbarLayout( Context context ) {

		super( context );
		initView();
	}

	public ToolbarLayout( Context context, AttributeSet attrs ) {

		super( context, attrs );
		initView();
	}

	public ToolbarLayout( Context context, AttributeSet attrs, int defStyleAttr ) {

		super( context, attrs, defStyleAttr );
		initView();
	}

	private void initView() {

		rootView = LayoutInflater.from(getContext()).inflate( R.layout.toolbar_castme, null );

		toolbar = (Toolbar) rootView.findViewById( R.id.toolbarLayout);
		view_shadow = rootView.findViewById( R.id.view_shadow );
		toolbar_frameLayout_content = (FrameLayout) rootView.findViewById( R.id.toolbar_frameLayout_content );

		addView( rootView );
	}

	@Override
	public void addView( @NonNull View child ) {

		if ( canAddChildView( child ) )
			addViewToContentView( child );
	}

	private boolean canAddChildView( View child ) {

		if ( rootView == child ) {

			super.addView( child, -1, generateMatchParentLayoutParams() );
			return false;
		}
		else if ( 0 < toolbar_frameLayout_content.getChildCount() ) {

			throw new IllegalStateException( "ToolbarLayout can host only one direct child" );
		}

		return true;
	}

	private LayoutParams generateMatchParentLayoutParams() {

		return new LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
	}

	private void addViewToContentView( View child ) {

		LayoutParams layoutParams = generateMatchParentLayoutParams();
		child.setLayoutParams( layoutParams );
		toolbar_frameLayout_content.addView( child );
	}

	@Override
	public void addView( @NonNull View child, int index ) {

		if ( canAddChildView( child ) )
			addViewToContentView( child );
	}

	@Override
	public void addView( @NonNull View child, int width, int height ) {

		if ( canAddChildView( child ) )
			addViewToContentView( child );
	}

	@Override
	public void addView( @NonNull View child, ViewGroup.LayoutParams params ) {

		if ( canAddChildView( child ) )
			addViewToContentView( child );
	}

	@Override
	public void addView( @NonNull View child, int index, ViewGroup.LayoutParams params ) {

		if ( canAddChildView( child ) )
			addViewToContentView( child );
	}

	public Toolbar getToolbar() {

		return toolbar;
	}

	public int getToolbarHeight() {

		return toolbar.getMeasuredHeight();
	}

	public void setAlpha( float alpha ) {

		toolbar.getBackground().setAlpha( (int) ( 255 * alpha ) );
		ViewHelper.setAlpha(view_shadow, alpha);
	}

	public void setShadow( boolean isShadow ) {

		if ( !isShadow )
			ViewHelper.setAlpha( view_shadow, 0 );
	}

	public void setExpand( boolean expand ) {

		Context context = getContext();

		if ( null != context ) {

			RelativeLayout.LayoutParams layoutParams
					= (RelativeLayout.LayoutParams) toolbar_frameLayout_content.getLayoutParams();

			if ( expand ) {

				layoutParams.topMargin = 0;
			}
			else {

				TypedArray array = context.getTheme()
						.obtainStyledAttributes( new int[]{ R.attr.actionBarSize } );
				layoutParams.topMargin = array.getDimensionPixelSize( 0, 0 );
				array.recycle();
			}

			toolbar_frameLayout_content.setLayoutParams( layoutParams );
		}
	}

}
