package com.mavlux.test.mvc.view.Settings;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mavlux.framework.view.FragmentLayout;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.Settings.SettingsFragment;

/**
 * Created by i3 on 2015-07-31.
 */
public class SettingsFragmentLayout extends FragmentLayout<SettingsFragment, SettingsFragmentLayoutListener>{

    public SettingsFragmentLayout(SettingsFragment settingsFragment, SettingsFragment settingsFragment1, LayoutInflater inflater, ViewGroup container) {
        super(settingsFragment, settingsFragment1, inflater, container);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void onLayoutInflated() {

    }
}
