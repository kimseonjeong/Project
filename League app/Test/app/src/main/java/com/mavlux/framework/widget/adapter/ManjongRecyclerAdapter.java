package com.mavlux.framework.widget.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class ManjongRecyclerAdapter<DATA extends ItemData, VIEW extends RecyclerItemView<DATA>> extends RecyclerView.Adapter<VIEW> {

	private Context context;
	private ArrayList<DATA> items;
	private Class<VIEW> viewClass;
	private int itemViewLayoutResId;

	public ManjongRecyclerAdapter( Context context, Class<VIEW> viewClass, int itemViewLayoutResId ) {

		this.context = context;
		this.items = new ArrayList<>();
		this.viewClass = viewClass;
		this.itemViewLayoutResId = itemViewLayoutResId;
	}

	@Override
	public VIEW onCreateViewHolder( ViewGroup parent, int viewType ) {

		return newItemViewInstance( parent );
	}

	private VIEW newItemViewInstance( ViewGroup parent ) {

		try {

			Constructor<VIEW> constructor = viewClass.getConstructor( Context.class, View.class );
			View view = LayoutInflater.from(context).inflate( itemViewLayoutResId, parent, false );
			return constructor.newInstance( context, view );
		}
		catch ( NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e ) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void onBindViewHolder( VIEW view, int position ) {

		view.setData( items.get( position ) );
	}

	@Override
	public int getItemCount() {

		return items.size();
	}

	public void setItems( List<DATA> items ) {

		this.items.clear();

		if ( null != items )
			this.items.addAll( items );

		notifyDataSetChanged();
	}

	public void add( DATA data ) {

		if ( null != data ) {

			items.add( data );
			notifyDataSetChanged();
		}
	}

	public void add( int index, DATA data ) {

		if ( 0 <= index && index <= items.size() && null != data ) {

			items.add( index, data );
			notifyDataSetChanged();
		}
	}

	public void addAll( List<DATA> items ) {

		if ( null != items && 0 < items.size() && this.items.addAll( items ) )
			notifyDataSetChanged();
	}

	public boolean remove( DATA data ) {

		boolean result = items.remove( data );

		if ( result )
			notifyDataSetChanged();

		return result;
	}

	public void clearItems() {

		items.clear();
		notifyDataSetChanged();
	}

}
