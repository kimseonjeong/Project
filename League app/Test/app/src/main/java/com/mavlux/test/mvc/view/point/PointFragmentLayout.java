package com.mavlux.test.mvc.view.point;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mavlux.framework.view.FragmentLayout;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.point.PointFragment;

/**
 * Created by i3 on 2015-07-28.
 */
public class PointFragmentLayout extends FragmentLayout<PointFragment, PointFragmentLayoutListener> {

    public PointFragmentLayout(PointFragment fragment, PointFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container) {
        super(fragment, layoutListener, inflater, container);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_point;
    }

    @Override
    protected void onLayoutInflated() {

    }

}
