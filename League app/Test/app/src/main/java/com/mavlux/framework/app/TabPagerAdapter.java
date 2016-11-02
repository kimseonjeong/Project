

package com.mavlux.framework.app;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import java.util.ArrayList;


public class TabPagerAdapter extends FragmentPagerAdapter implements OnPageChangeListener, OnTabChangeListener {
	
	private TabHost tabHost;
	private ViewPager viewPager;
	
	private ArrayList<TabInfo> items;
	
	public TabPagerAdapter( FragmentManager fragmentManager, TabHost tabHost, ViewPager viewPager ) {
		
		super( fragmentManager );
		
		this.tabHost = tabHost;
		this.viewPager = viewPager;
		items = new ArrayList<TabInfo>();
		
		tabHost.setup();
		tabHost.setOnTabChangedListener( this );
		viewPager.setOnPageChangeListener( this );
		viewPager.setAdapter( this );
	}
	
	public void addItem( TabInfo tabInfo ) {
	
		items.add( tabInfo );
		tabHost.addTab( tabInfo.tabSpec );
		notifyDataSetChanged();
	}
	
	@Override
	public Fragment getItem( int position ) {
	
		return items.get( position ).fragment;
	}
	
	@Override
	public int getCount() {
	
		return items.size();
	}
	
	@Override
	public void onTabChanged( String tabId ) {
	
		viewPager.setCurrentItem( tabHost.getCurrentTab() );
	}
	
	@Override
	public void onPageSelected( int position ) {
	
		tabHost.setCurrentTab( position );
	}
	
	@Override
	public void onPageScrollStateChanged( int state ) {
	
	}
	
	@Override
	public void onPageScrolled( int position, float positionoffset, int positionOffsetPixels ) {
	
	}
	
	public static class DummyTabFactory implements TabHost.TabContentFactory {
		
		private final Context context;
		
		public DummyTabFactory( Context context ) {
		
			this.context = context;
		}
		
		@Override
		public View createTabContent( String tag ) {
		
			View view = new View( context );
			view.setMinimumWidth( 0 );
			view.setMinimumHeight( 0 );
			
			return view;
		}
		
	}
	
	public static class TabInfo {
		
		private TabSpec tabSpec;
		private Fragment fragment;
		
		public TabInfo( Context context, TabSpec tabSpec, Fragment fragment ) {
		
			this.tabSpec = tabSpec;
			this.fragment = fragment;
			
			this.tabSpec.setContent( new DummyTabFactory( context ) );
		}
		
		public TabSpec getTabSpec() {
		
			return tabSpec;
		}
		
		public Fragment getFragment() {
		
			return fragment;
		}
		
	}
	
}
