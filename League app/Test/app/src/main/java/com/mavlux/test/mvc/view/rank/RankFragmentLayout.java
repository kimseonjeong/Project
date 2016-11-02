package com.mavlux.test.mvc.view.rank;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mavlux.framework.view.FragmentLayout;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.rank.RankFragment;

/**
 * Created by i3 on 2015-07-28.
 */
public class RankFragmentLayout extends FragmentLayout<RankFragment, RankFragmentLayoutListener> {

    public RankFragmentLayout(RankFragment fragment, RankFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container) {
        super(fragment, layoutListener, inflater, container);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_rank;
    }

    @Override
    protected void onLayoutInflated() {

    }
}
