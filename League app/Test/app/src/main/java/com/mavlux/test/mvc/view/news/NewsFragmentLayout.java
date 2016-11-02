package com.mavlux.test.mvc.view.news;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mavlux.framework.view.FragmentLayout;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.news.NewsFragment;

/**
 * Created by i3 on 2015-07-28.
 */
public class NewsFragmentLayout extends FragmentLayout<NewsFragment, NewsFragmentLayoutListener> {

    public NewsFragmentLayout(NewsFragment fragment, NewsFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container) {
        super(fragment, layoutListener, inflater, container);
    }

    @Override
    protected int getLayoutResId() {

        return R.layout.fragment_news;
    }

    @Override
    protected void onLayoutInflated() {

    }
}
