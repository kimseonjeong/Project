package com.mavlux.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;


public class ObservationListView extends ListView {

    private boolean isLock;

    public ObservationListView(Context context) {

        this(context, null);
    }

    public ObservationListView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        //Log.wtf("listView accept Intercepter touch event", "" + ev.getAction());

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        //Log.wtf("listView accept touch event", "" + ev.getAction());

        if (isLock)
            return false;
        else
            return super.onTouchEvent(ev);
    }

    public void setIsLock(boolean isLock) {

        this.isLock = isLock;
    }

}
