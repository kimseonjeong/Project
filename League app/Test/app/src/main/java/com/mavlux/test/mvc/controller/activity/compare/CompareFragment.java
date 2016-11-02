package com.mavlux.test.mvc.controller.activity.compare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mavlux.framework.controller.BaseFragment;
import com.mavlux.framework.util.OttoUtil;
import com.mavlux.test.mvc.event.OnCheerTopScroll;
import com.mavlux.test.mvc.event.OnInfoTopScroll;
import com.mavlux.test.mvc.event.OnPredictTopScroll;
import com.mavlux.test.mvc.view.compare.CompareFragmentLayout;
import com.mavlux.test.mvc.view.compare.CompareFragmentLayoutListener;
import com.squareup.otto.Subscribe;

public class CompareFragment extends BaseFragment implements CompareFragmentLayoutListener{

    private CompareFragmentLayout fragmentLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        fragmentLayout = new CompareFragmentLayout(this, this, inflater, container);
        OttoUtil.getInstance().register(this);

        return fragmentLayout.getRootView();
    }


    public static CompareFragment newInstance(){

        return new CompareFragment();
    }

    @Subscribe
    public void OnPredictTopScroll( OnPredictTopScroll onPredictTopScroll){

        fragmentLayout.setListViewLock(false);

    }

    @Subscribe
    public void OnInfoTopScroll( OnInfoTopScroll onInfoTopScroll ){

        fragmentLayout.setListViewLock(false);

    }

    @Subscribe
    public void OnCheerTopScroll ( OnCheerTopScroll onCheerTopScroll ){

        fragmentLayout.setListViewLock(false);

    }

    @Override
    public void onDestroyView(){

        OttoUtil.getInstance().unregister(this);
        super.onDestroyView();

    }

}
