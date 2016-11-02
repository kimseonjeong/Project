package com.mavlux.test.mvc.controller.activity.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mavlux.framework.controller.BaseFragment;
import com.mavlux.test.mvc.view.Settings.SettingsFragmentLayout;
import com.mavlux.test.mvc.view.Settings.SettingsFragmentLayoutListener;

/**
 * Created by i3 on 2015-07-31.
 */
public class SettingsFragment extends BaseFragment implements SettingsFragmentLayoutListener{

    private SettingsFragmentLayout fragmentLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        fragmentLayout = new SettingsFragmentLayout(this,this,inflater, container);

        return fragmentLayout.getRootView();
    }

    public static SettingsFragment newInstance(){

        return new SettingsFragment();

    }

}
