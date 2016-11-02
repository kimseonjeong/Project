package com.mavlux.test.mvc.view.home;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;

import com.mavlux.framework.view.LayoutListener;

/**
 * Created by i3 on 2015-07-28.
 */
public interface HomeFragmentLayoutListener extends LayoutListener {
    PagerAdapter getGameAdapter();
    PagerAdapter getListAdapter();
}
