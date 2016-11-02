package com.mavlux.framework.widget.adapter;


import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import java.util.ArrayList;


public class OnScrollDelegate extends RecyclerView.OnScrollListener implements OnScrollListener {

	private ArrayList<OnScrollListener> onScrollListeners = new ArrayList<>();
	private ArrayList<RecyclerView.OnScrollListener> onRecyclerScrollListeners = new ArrayList<>();

	@Override
	public void onScrollStateChanged( RecyclerView recyclerView, int newState ) {

		for ( RecyclerView.OnScrollListener onScrollListener : onRecyclerScrollListeners )
			onScrollListener.onScrollStateChanged( recyclerView, newState );
	}

	@Override
	public void onScrolled( RecyclerView recyclerView, int dx, int dy ) {

		for ( RecyclerView.OnScrollListener onScrollListener : onRecyclerScrollListeners )
			onScrollListener.onScrolled( recyclerView, dx, dy );
	}

	@Override
	public void onScrollStateChanged( AbsListView view, int scrollState ) {

		for ( OnScrollListener onScrollListener : onScrollListeners )
			onScrollListener.onScrollStateChanged( view, scrollState );
	}

	@Override
	public void onScroll( AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount ) {

		for ( OnScrollListener onScrollListener : onScrollListeners )
			onScrollListener.onScroll( view, firstVisibleItem, visibleItemCount, totalItemCount );
	}

	public void addOnScrollListener( RecyclerView.OnScrollListener onScrollListener ) {

		if ( null != onScrollListener )
			onRecyclerScrollListeners.add( onScrollListener );
	}

	public void addOnScrollListener( OnScrollListener onScrollListener ) {

		if ( null != onScrollListener )
			onScrollListeners.add( onScrollListener );
	}

	public boolean removeOnScrollListener( RecyclerView.OnScrollListener onScrollListener ) {

		if ( null != onScrollListener )
			return onRecyclerScrollListeners.remove( onScrollListener );
		else
			return false;
	}

	public boolean removeOnScrollListener( OnScrollListener onScrollListener ) {

		if ( null != onScrollListener )
			return onScrollListeners.remove( onScrollListener );
		else
			return false;
	}

}
