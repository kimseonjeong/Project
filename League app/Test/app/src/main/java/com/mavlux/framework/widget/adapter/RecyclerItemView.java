package com.mavlux.framework.widget.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class RecyclerItemView<DATA extends ItemData> extends RecyclerView.ViewHolder {

	private Context context;
	private View itemView;

	private DATA data;

	public RecyclerItemView( Context context, View itemView ) {

		super( itemView );
		this.context = context;
		this.itemView = itemView;
	}

	public void setData( DATA data ) {

		this.data = data;
		refreshView( data );
	}

	protected abstract void refreshView( DATA data );

	public DATA getData() {

		return data;
	}

	public Context getContext() {

		return context;
	}

	public View getItemView() {

		return itemView;
	}

	public View findViewById( int resId ) {

		return itemView.findViewById( resId );
	}

}
