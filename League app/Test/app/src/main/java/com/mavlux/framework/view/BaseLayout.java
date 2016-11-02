package com.mavlux.framework.view;


import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


public abstract class BaseLayout implements View.OnTouchListener {

	private View rootView;
	private Toast toast;

	protected View findViewById( int resId ) {

		return rootView.findViewById( resId );
	}

	public View getRootView() {

		return rootView;
	}

	public void setRootView( View rootView ) {

		this.rootView = rootView;
		rootView.setOnTouchListener( this );
	}

	@Override
	public boolean onTouch( View view, MotionEvent event ) {

		return false;
	}

	public void showToast( int text ) {

		showToast( getContext().getString( text ) );
	}

	public void showToast( CharSequence text ) {

		if ( null != toast )
			toast.cancel();

		toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
		toast.show();
	}

	public void hideSoftKey() {

		InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService( Context.INPUT_METHOD_SERVICE );
		View currentFocus = getActivity().getCurrentFocus();
		if ( null != currentFocus )
			inputMethodManager.hideSoftInputFromWindow( currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS );
	}

	protected abstract View inflateLayout( int layoutResId );

	protected abstract int getLayoutResId();

	protected abstract void onLayoutInflated();

	protected abstract Context getContext();

	protected abstract Activity getActivity();

}
