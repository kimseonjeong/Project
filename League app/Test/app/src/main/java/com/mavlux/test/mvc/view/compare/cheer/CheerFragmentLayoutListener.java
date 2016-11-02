package com.mavlux.test.mvc.view.compare.cheer;

import android.widget.ListAdapter;

import com.mavlux.framework.view.LayoutListener;

public interface CheerFragmentLayoutListener extends LayoutListener {
    ListAdapter getAdapter(int value, String date, String time, String content);

}
