package com.mavlux.test.mvc.view.compare;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mavlux.framework.util.OttoUtil;
import com.mavlux.framework.view.FragmentLayout;
import com.mavlux.framework.widget.scrollobservableview.ObservableRecyclerView;
import com.mavlux.framework.widget.scrollobservableview.ObservableScrollView;
import com.mavlux.framework.widget.scrollobservableview.OnTouchScrollListener;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.compare.CompareFragment;
import com.mavlux.test.mvc.controller.activity.compare.cheer.CheerFragment;
import com.mavlux.test.mvc.controller.activity.compare.info.InfoFragment;
import com.mavlux.test.mvc.controller.activity.compare.predict.PredictFragment;
import com.mavlux.test.mvc.event.OnLastScroll;


public class CompareFragmentLayout extends FragmentLayout<CompareFragment, CompareFragmentLayoutListener> implements View.OnClickListener, OnTouchListener {


    private TextView textView_predict, textView_info, textView_cheer;
    private static ObservableScrollView scrollView;
    private LinearLayout linearLayout_compare_layout, linearLayout_compare_layout_top;
    private FrameLayout linearLayout_compare;

    private int height, height_layout_top, height_frame;

    public CompareFragmentLayout(CompareFragment fragment, CompareFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container) {
        super(fragment, layoutListener, inflater, container);
    }

    @Override
    protected int getLayoutResId() {

        return R.layout.fragment_compare;
    }

    @Override
    protected void onLayoutInflated() {
        initView();
    }

    private void initView() {

        linearLayout_compare_layout = (LinearLayout) findViewById(R.id.linearLayout_compare_layout);
        linearLayout_compare_layout_top = (LinearLayout) findViewById(R.id.linearLayout_compare_layout_top);
        linearLayout_compare = (FrameLayout) findViewById(R.id.linearLayout_compare);

        setDisplay();

        scrollView = (ObservableScrollView) findViewById(R.id.scrollView_compare);
        scrollView.setOnTouchScrollListener(new OnTouchScrollListener() {
            @Override
            public void onTouchDown(int scrollY) {

            }

            @Override
            public boolean onScrollUp(float touchDeltaY, int scrollY) {
                return false;
            }

            @Override
            public boolean onScrollStop(int scrollY) {
                return false;
            }

            @SuppressLint("LongLogTag")
            @Override
            public boolean onScrollDown(float touchDeltaY, int scrollY) {

                if ((height_layout_top * 0.5) < scrollY) {

                    scrollView.setIsLock(true);
                    OttoUtil.getInstance().postInMainThread(new OnLastScroll());
                }

                Log.wtf("scroll", "Y : " + scrollY);
                Log.e("linearLayout_compare_top: ", height_layout_top * 0.5 + "");
                return false;
            }

            @Override
            public boolean onTouchUp(float touchDeltaY, int scrollY) {
                return false;
            }
        });


        getFragment().getFragmentManager().beginTransaction().replace(R.id.linearLayout_compare, PredictFragment.newInstance()).commit();


        textView_predict = (TextView) findViewById(R.id.textView_compare_predict);
        textView_predict.setOnClickListener(this);

        textView_info = (TextView) findViewById(R.id.textView_compare_info);
        textView_info.setOnClickListener(this);

        textView_cheer = (TextView) findViewById(R.id.textView_compare_cheer);
        textView_cheer.setOnClickListener(this);

        setPredict();

    }

    private void setDisplay() {

        DisplayMetrics dm = getActivity().getApplication().getResources().getDisplayMetrics();

        height = dm.heightPixels;

        height_layout_top = (int) (height * 0.2);
        height_frame = height - height_layout_top;


        linearLayout_compare_layout.getLayoutParams().height = height;
        linearLayout_compare_layout.requestLayout();

        linearLayout_compare_layout_top.getLayoutParams().height = height_layout_top;
        linearLayout_compare_layout_top.requestLayout();

        linearLayout_compare.getLayoutParams().height = height_frame;
        linearLayout_compare.requestLayout();

        Log.e("height:", "height : " + height + " height_layout_top : " + height_layout_top + " height_frame" + height_frame);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.textView_compare_predict:
                setPredict();
                break;

            case R.id.textView_compare_info:
                setInfo();
                break;

            case R.id.textView_compare_cheer:
                setCheer();
                break;

        }


    }


    public void setPredict() {

        getFragment().getFragmentManager().beginTransaction().replace(R.id.linearLayout_compare, PredictFragment.newInstance()).commit();


        textView_predict.setTextColor(Color.WHITE);
        textView_predict.setBackgroundColor(Color.BLACK);

        textView_info.setTextColor(Color.GRAY);
        textView_info.setBackgroundResource(R.color.gray_transparent_big);

        textView_cheer.setTextColor(Color.GRAY);
        textView_cheer.setBackgroundResource(R.color.gray_transparent_big);

    }

    public void setInfo() {

        getFragment().getFragmentManager().beginTransaction().replace(R.id.linearLayout_compare, InfoFragment.newInstance()).commit();

        textView_predict.setTextColor(Color.GRAY);
        textView_predict.setBackgroundResource(R.color.gray_transparent_big);

        textView_cheer.setTextColor(Color.GRAY);
        textView_cheer.setBackgroundResource(R.color.gray_transparent_big);

        textView_info.setTextColor(Color.WHITE);
        textView_info.setBackgroundColor(Color.BLACK);

    }

    public void setCheer() {

        getFragment().getFragmentManager().beginTransaction().replace(R.id.linearLayout_compare, CheerFragment.newInstance()).commit();

        textView_predict.setTextColor(Color.GRAY);
        textView_predict.setBackgroundResource(R.color.gray_transparent_big);

        textView_info.setTextColor(Color.GRAY);
        textView_info.setBackgroundResource(R.color.gray_transparent_big);

        textView_cheer.setTextColor(Color.WHITE);
        textView_cheer.setBackgroundColor(Color.BLACK);

    }


    public void setListViewLock(boolean Islock) {

        scrollView.setIsLock(Islock);

    }

    public static void setScrollBottom(){

        scrollView.fullScroll(View.FOCUS_DOWN);
    }


}
