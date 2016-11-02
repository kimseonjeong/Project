package com.mavlux.test.mvc.controller.activity.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mavlux.framework.controller.BaseFragment;
import com.mavlux.test.mvc.view.news.NewsFragmentLayout;
import com.mavlux.test.mvc.view.news.NewsFragmentLayoutListener;

public class NewsFragment extends BaseFragment implements NewsFragmentLayoutListener{

    private NewsFragmentLayout fragmentLayout;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        fragmentLayout = new NewsFragmentLayout(this,this,inflater,container);

        return fragmentLayout.getRootView();
    }

    public static NewsFragment newInstance(){

        return new NewsFragment();
    }
}
