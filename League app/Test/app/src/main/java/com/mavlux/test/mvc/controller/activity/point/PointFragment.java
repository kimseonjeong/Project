package com.mavlux.test.mvc.controller.activity.point;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mavlux.framework.controller.BaseFragment;
import com.mavlux.test.R;
import com.mavlux.test.mvc.view.point.PointFragmentLayout;
import com.mavlux.test.mvc.view.point.PointFragmentLayoutListener;


public class PointFragment extends BaseFragment implements PointFragmentLayoutListener {

    private PointFragmentLayout fragmentLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        fragmentLayout= new PointFragmentLayout(this,this,inflater, container );


        return fragmentLayout.getRootView();
    }

    public static PointFragment newInstance(){

        return new PointFragment();
    }

}
