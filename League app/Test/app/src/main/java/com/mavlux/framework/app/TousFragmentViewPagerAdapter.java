package com.mavlux.framework.app;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;


public class TousFragmentViewPagerAdapter extends FragmentViewPagerAdapter {

	private ArrayList<Fragment> items = new ArrayList<>();

	public TousFragmentViewPagerAdapter( FragmentManager fm ) {

		super( fm );
	}

	@Override
	public int getCount() {

		return items.size();
	}

	@Override
	public Fragment getItem( int position ) {

		return items.get( position );
	}

	public void addItem( Fragment fragment ) {

		items.add( fragment );
		notifyDataSetChanged();
	}

	public boolean removeItem( Fragment fragment ) {

		if ( items.remove( fragment ) ) {

			notifyDataSetChanged();
			return true;
		}
		else {

			return false;
		}
	}

}
