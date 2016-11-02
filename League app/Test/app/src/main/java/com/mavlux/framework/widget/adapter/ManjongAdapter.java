package com.mavlux.framework.widget.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class ManjongAdapter<DATA extends ItemData> extends BaseAdapter {

	private Context context;
	private Class<? extends ItemView<DATA>> viewClass;
	private List<DATA> list;

	public ManjongAdapter( Context context, Class<? extends ItemView<DATA>> viewClass ) {

		this.context = context;
		this.viewClass = viewClass;
		this.list = new ArrayList<>();
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem( int position ) {

		return list.get( position );
	}

	@Override
	public long getItemId( int position ) {

		try {

			return list.get( position ).getItemId();
		}
		catch ( IndexOutOfBoundsException exception ) {

			return -1;
		}
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {

		@SuppressWarnings("unchecked")
		ItemView<DATA> view = (ItemView<DATA>) convertView;

		if ( null == view )
			view = newItemViewInstance( parent );

		view.setData( getItemData( position ) );

		return view;
	}

	protected ItemView<DATA> newItemViewInstance( ViewGroup parent ) {

		try {

			Constructor<? extends ItemView<DATA>> constructor = viewClass.getConstructor( Context.class );
			return constructor.newInstance( context );
		}
		catch ( InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e ) {
			e.printStackTrace();
		}

		return null;
	}

	public Context getContext() {

		return context;
	}

	public List<DATA> getItemDatas() {

		return list;
	}

	public void setItemDatas( List<DATA> datas ) {

		list.clear();
		if ( null != datas && 0 < datas.size() )
			list.addAll( datas );
		notifyDataSetChanged();
	}

	public DATA getItemData( int position ) {

		return list.get( position );
	}

	public void addItemData( DATA data ) {

		list.add( data );
		notifyDataSetChanged();
	}

	public void insertItemData( DATA data, int index ) {

		list.add( index, data );
		notifyDataSetChanged();
	}

	public boolean removeItemData( DATA data ) {

		boolean result = list.remove( data );
		notifyDataSetChanged();
		return result;
	}

	public void clear() {

		list.clear();
		notifyDataSetChanged();
	}

}
