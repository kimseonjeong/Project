package com.mavlux.test.mvc.controller.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mavlux.framework.controller.BaseFragment;
import com.mavlux.test.mvc.view.home.HomeFragmentLayout;
import com.mavlux.test.mvc.view.home.HomeFragmentLayoutListener;
import com.mavlux.test.mvc.view.main.MainActivityLayout;

public class HomeFragment extends BaseFragment implements HomeFragmentLayoutListener {

    private HomeFragmentLayout fragmentLayout;

    public static HomeFragment newInstance(){

        return new HomeFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        fragmentLayout = new HomeFragmentLayout(this,this,inflater,container);
        MainActivityLayout.setTitle("메인");

        return fragmentLayout.getRootView();
    }

    public PagerAdapter getGameAdapter() {

        return new MyGamePagerAdapter(getFragmentManager());
    }

    public PagerAdapter getListAdapter(){

        return new MyListPagerAdapter(getFragmentManager());
    }

    private class MyGamePagerAdapter extends FragmentStatePagerAdapter{

        public MyGamePagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return HomeImageFragment.newInstance().setPosition(position);
        }

        @Override
        public int getCount() {

            return 5;
        }

    }

    private class MyListPagerAdapter extends FragmentStatePagerAdapter{

        public MyListPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return HomeListFragment.newInstance().setPosition(position);
        }

        @Override
        public int getCount() {

            return 100000000;
        }

    }

}
