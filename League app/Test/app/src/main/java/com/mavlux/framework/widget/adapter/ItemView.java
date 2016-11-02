package com.mavlux.framework.widget.adapter;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public abstract class ItemView<DATA extends ItemData> extends FrameLayout {

	public ItemView( Context context ) {

		this( context, null );
	}

	public ItemView( Context context, int layoutResId ) {

		this( context, null, layoutResId );
	}

	public ItemView( Context context, AttributeSet attrs ) {

		super( context, attrs );
	}

	public ItemView( Context context, AttributeSet attrs, int layoutResId ) {

		this( context, attrs );
		inflateView( layoutResId );
		onInflated();
	}

	private void inflateView( int layoutResId ) {

		inflate( getContext(), layoutResId, this );
	}

	protected void onInflated() {

	}

	public abstract void setData( DATA data );

}
