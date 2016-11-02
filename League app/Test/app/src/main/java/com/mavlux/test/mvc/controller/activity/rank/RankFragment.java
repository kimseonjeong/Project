package com.mavlux.test.mvc.controller.activity.rank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mavlux.framework.controller.BaseFragment;
import com.mavlux.test.mvc.view.rank.RankFragmentLayout;
import com.mavlux.test.mvc.view.rank.RankFragmentLayoutListener;

public class RankFragment extends BaseFragment implements RankFragmentLayoutListener{

    private RankFragmentLayout fragmentLayout;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        fragmentLayout = new RankFragmentLayout(this, this, inflater, container);

        return fragmentLayout.getRootView();
    }

    public static RankFragment newInstance(){
        return new RankFragment();

    }
}
