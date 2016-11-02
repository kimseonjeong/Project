package com.mavlux.framework.view;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;



public abstract class FragmentLayout<FRAGMENT extends Fragment, LAYOUT_LISTENER extends LayoutListener> extends BaseLayout {

	private FRAGMENT fragment;
	private LAYOUT_LISTENER layoutListener;
	private LayoutInflater layoutInflater;
	private ViewGroup root;
	private AlertDialog loadingDialog;

	public FragmentLayout( FRAGMENT fragment, LAYOUT_LISTENER layoutListener, LayoutInflater inflater, ViewGroup container ) {

		this.fragment = fragment;
		this.layoutListener = layoutListener;
		this.layoutInflater = inflater;
		this.root = container;

		inflateLayout( getLayoutResId() );
	}

	@Override
	protected final View inflateLayout( int layoutResId ) {

		View rootView = layoutInflater.inflate( layoutResId, root, false );
		if ( null != rootView ) {

			setRootView( rootView );
			onLayoutInflated();
		}

		return getRootView();
	}

	@Override
	protected final Context getContext() {

		return getFragment().getActivity();
	}

	@Override
	protected final Activity getActivity() {

		return getFragment().getActivity();
	}

	protected final FRAGMENT getFragment() {

		return fragment;
	}

	protected final LAYOUT_LISTENER getLayoutListener() {

		return layoutListener;
	}

	public void onCreateContextMenu( ContextMenu contextMenu, View view, ContextMenuInfo contextMenuInfo ) {

	}

	public boolean onContextItemSelected( MenuItem menuItem ) {

		return false;
	}

	public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

	}

	public boolean onOptionsItemSelected( MenuItem item ) {

		return false;
	}

}
