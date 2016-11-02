package com.mavlux.test.mvc.controller.activity.compare.cheer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.mavlux.framework.controller.BaseFragment;
import com.mavlux.framework.util.OttoUtil;
import com.mavlux.test.mvc.event.OnLastScroll;
import com.mavlux.test.mvc.view.compare.cheer.CheerFragmentLayout;
import com.mavlux.test.mvc.view.compare.cheer.CheerFragmentLayoutListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;


public class CheerFragment extends BaseFragment implements CheerFragmentLayoutListener {

    private CheerFragmentLayout fragmentLayout;

    private ArrayList<MyCheerList> data = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentLayout = new CheerFragmentLayout(this, this, inflater, container);
        OttoUtil.getInstance().register(this);

        return fragmentLayout.getRootView();
    }

    public static CheerFragment newInstance() {

        return new CheerFragment();
    }

    @Override
    public ListAdapter getAdapter(int value, String date, String time, String content) {

        data.add(MyCheerList.newInstance().setValue(value).setName("name").setDate(date).setTime(time).setContent(content));

        MyCheerListAdapter adapter = new MyCheerListAdapter(LayoutInflater.from(getActivity()), data);
        return adapter;
    }

    @Subscribe
    public void OnLastScroll(OnLastScroll onLastScroll) {

        fragmentLayout.setListViewLock(false);
    }

    @Override
    public void onDestroyView() {

        OttoUtil.getInstance().unregister(this);
        super.onDestroyView();
    }
}
