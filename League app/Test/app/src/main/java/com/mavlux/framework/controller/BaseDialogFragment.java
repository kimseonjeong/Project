package com.mavlux.framework.controller;


import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;


public class BaseDialogFragment extends DialogFragment {

	private OnDismissDialogListener onDismissDialogListener;

	private BaseActivity getBaseActivity() {

		FragmentActivity activity = getActivity();

		if ( activity instanceof BaseActivity )
			return (BaseActivity) activity;
		else
			return null;
	}

	@Override
	public void onDismiss( DialogInterface dialog ) {

		super.onDismiss( dialog );

		if ( null != onDismissDialogListener )
			onDismissDialogListener.onDismiss();
	}

	public void setOnDismissDialogListener( OnDismissDialogListener onDismissDialogListener ) {

		this.onDismissDialogListener = onDismissDialogListener;
	}

	public interface OnDismissDialogListener {

		public void onDismiss();

	}

}
