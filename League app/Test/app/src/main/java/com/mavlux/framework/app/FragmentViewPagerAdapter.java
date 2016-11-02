package com.mavlux.framework.app;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class FragmentViewPagerAdapter extends PagerAdapter {

	private static final String TAG = "FragmentStatePagerAdapter";
	private static final boolean DEBUG = false;

	private final FragmentManager mFragmentManager;
	private FragmentTransaction mCurTransaction = null;

	private ArrayList<Fragment.SavedState> mSavedState = new ArrayList<>();
	private ArrayList<Fragment> mFragments = new ArrayList<>();
	private Fragment mCurrentPrimaryItem = null;

	public FragmentViewPagerAdapter( FragmentManager fm ) {

		mFragmentManager = fm;
	}

	@Override
	public void startUpdate( ViewGroup container ) {

	}

	@Override
	public int getCount() {

		return mFragments.size();
	}

	@SuppressLint( { "CommitTransaction", "LongLogTag" } )
	@Override
	public Object instantiateItem( ViewGroup container, int position ) {

		Fragment fragment = getItem( position );

		if ( mFragments.size() <= position || fragment != mFragments.get( position ) ) {

			if ( mCurTransaction == null )
				mCurTransaction = mFragmentManager.beginTransaction();

			if ( DEBUG )
				Log.v(TAG, "Adding item #" + position + ": f=" + fragment);

			if ( mSavedState.size() > position ) {

				Fragment.SavedState fss = mSavedState.get( position );

				if ( fss != null )
					fragment.setInitialSavedState( fss );
			}

			while ( mFragments.size() <= position )
				mFragments.add( null );

			fragment.setMenuVisibility( false );
			fragment.setUserVisibleHint( false );

			mFragments.set( position, fragment );
			mCurTransaction.add( container.getId(), fragment );
		}

		return fragment;
	}

	@SuppressLint( { "CommitTransaction", "LongLogTag" } )
	@Override
	public void destroyItem( ViewGroup container, int position, Object object ) {

		Fragment fragment = (Fragment) object;

		if ( mCurTransaction == null )
			mCurTransaction = mFragmentManager.beginTransaction();

		if ( DEBUG )
			Log.v(TAG, "Removing item #" + position + ": f=" + object + " v=" + ((Fragment) object).getView());

		while ( mSavedState.size() <= position )
			mSavedState.add( null );

		mSavedState.set( position, mFragmentManager.saveFragmentInstanceState( fragment ) );
		mFragments.set( position, null );

		mCurTransaction.remove( fragment );
	}

	@Override
	public void setPrimaryItem( ViewGroup container, int position, Object object ) {

		Fragment fragment = (Fragment) object;

		if ( fragment != mCurrentPrimaryItem ) {

			if ( mCurrentPrimaryItem != null ) {

				mCurrentPrimaryItem.setMenuVisibility( false );
				mCurrentPrimaryItem.setUserVisibleHint( false );
			}

			if ( fragment != null ) {

				fragment.setMenuVisibility( true );
				fragment.setUserVisibleHint( true );
			}

			mCurrentPrimaryItem = fragment;
		}
	}

	@Override
	public void finishUpdate( ViewGroup container ) {

		if ( mCurTransaction != null ) {

			mCurTransaction.commitAllowingStateLoss();
			mCurTransaction = null;
			mFragmentManager.executePendingTransactions();
		}
	}

	@Override
	public boolean isViewFromObject( View view, Object object ) {

		return ( (Fragment) object ).getView() == view;
	}

	@Override
	public Parcelable saveState() {

		Bundle state = null;
		if ( mSavedState.size() > 0 ) {

			state = new Bundle();
			Fragment.SavedState[] fss = new Fragment.SavedState[mSavedState.size()];
			mSavedState.toArray( fss );
			state.putParcelableArray( "states", fss );
		}

		for ( int i = 0; i < mFragments.size(); i++ ) {

			Fragment f = mFragments.get( i );

			if ( f != null ) {

				if ( state == null )
					state = new Bundle();

				String key = "f" + i;
				mFragmentManager.putFragment( state, key, f );
			}
		}

		return state;
	}

	@SuppressLint( "LongLogTag" )
	@Override
	public void restoreState( Parcelable state, ClassLoader loader ) {

		if ( state != null ) {

			Bundle bundle = (Bundle) state;
			bundle.setClassLoader( loader );

			Parcelable[] fss = bundle.getParcelableArray( "states" );
			mSavedState.clear();
			mFragments.clear();

			if ( fss != null ) {

				for ( int i = 0; i < fss.length; i++ )
					mSavedState.add( (Fragment.SavedState) fss[i] );
			}

			Iterable<String> keys = bundle.keySet();

			for ( String key : keys ) {

				if ( key.startsWith( "f" ) ) {

					int index = Integer.parseInt(key.substring(1));
					Fragment f = mFragmentManager.getFragment( bundle, key );

					if ( f != null ) {

						while ( mFragments.size() <= index )
							mFragments.add( null );

						f.setMenuVisibility( false );
						mFragments.set( index, f );
					}
					else {

						Log.w(TAG, "Bad fragment at key " + key);
					}
				}
			}
		}
	}

	public void addItem( Fragment fragment ) {

		if ( null != fragment && !mFragments.contains( fragment ) ) {

			mFragments.add( fragment );
			notifyDataSetChanged();
		}
	}

	public Fragment getItem( int position ) {

		return mFragments.get( position );
	}

	public boolean removeItem( Fragment fragment ) {

		boolean result = false;

		if ( null != fragment )
			result = mFragments.remove( fragment );

		if ( result )
			notifyDataSetChanged();

		return result;
	}

}
