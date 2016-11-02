package com.mavlux.test.mvc.view.compare.cheer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mavlux.framework.util.OttoUtil;
import com.mavlux.framework.view.FragmentLayout;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.compare.cheer.CheerFragment;
import com.mavlux.test.mvc.event.OnCheerTopScroll;
import com.mavlux.test.mvc.view.compare.CompareFragmentLayout;
import com.mavlux.widget.ObservationListView;

import java.util.Calendar;

public class CheerFragmentLayout extends FragmentLayout<CheerFragment, CheerFragmentLayoutListener> implements View.OnClickListener, AbsListView.OnScrollListener {

    private ObservationListView observationListView;
    private LinearLayout linearLayout_rootview, linearLayout_contentview;
    private ImageView imageView_cheer_1, imageView_cheer_2;
    private Button imageButton_cheer_1, imageButton_cheer_2;
    private EditText editText;
    private Button button;

    private int value = 0;
    private String str_content;
    private String str_date, str_time;

    private InputMethodManager imm;

    public CheerFragmentLayout(CheerFragment fragment, CheerFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container) {
        super(fragment, layoutListener, inflater, container);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_cheer;
    }

    @Override
    protected void onLayoutInflated() {
        initView();
    }

    private void initView() {

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        linearLayout_rootview = (LinearLayout) findViewById(R.id.linearLayout_cheer);
        linearLayout_contentview = (LinearLayout) findViewById(R.id.linearLayout_cheer_content);

        observationListView = (ObservationListView) findViewById(R.id.listView_cheer);
        observationListView.setAdapter(getLayoutListener().getAdapter(2,"7월28일","16:56","연패는 하지말자!"));
        observationListView.setIsLock(true);

        imageView_cheer_1 = (ImageView) findViewById(R.id.imageView_cheer_image_1);
        imageView_cheer_1.setImageResource(R.drawable.game_4);

        imageView_cheer_2 = (ImageView) findViewById(R.id.imageView_cheer_image_2);
        imageView_cheer_2.setImageResource(R.drawable.game_3);

        imageButton_cheer_1 = (Button) findViewById(R.id.imageButton_cheer_button_1);
        imageButton_cheer_2 = (Button) findViewById(R.id.imageButton_cheer_button_2);

        editText = (EditText) findViewById(R.id.editText_cheer);
        button = (Button) findViewById(R.id.button_cheer);

        imageButton_cheer_1.setOnClickListener(this);
        imageButton_cheer_2.setOnClickListener(this);
        linearLayout_rootview.setOnTouchListener(this);
        button.setOnClickListener(this);
        observationListView.setOnScrollListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                OttoUtil.getInstance().postInMainThread(new OnCheerTopScroll());
                break;
        }
        return super.onTouch(view, event);
    }

    public void setListViewLock(boolean isLock) {

        observationListView.setIsLock(isLock);

    }

    public void setContentView() {

        linearLayout_contentview.setVisibility(View.VISIBLE);
        CompareFragmentLayout.setScrollBottom();

    }

    public void setContentNoView() {

        linearLayout_contentview.setVisibility(View.GONE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imageButton_cheer_button_1:
                setPress_1();
                break;

            case R.id.imageButton_cheer_button_2:
                setPress_2();
                break;

            case R.id.button_cheer:
                setUploadContent();
                break;
        }

    }

    private void setPress_1() {

        setContentView();
        value = 1;

    }

    private void setPress_2() {

        setContentView();
        value = 2;

    }

    private void setUploadContent() {

        str_content = editText.getText().toString();
        setContentNoView();

        Calendar calendar = Calendar.getInstance();

        str_date = (calendar.get(Calendar.MONTH) + 1) + "월" + calendar.get(Calendar.DAY_OF_MONTH) + "일";
        str_time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        observationListView.setAdapter(getLayoutListener().getAdapter(value, str_date, str_time, str_content));

        editText.setText("");
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && observationListView.getTop() == 0) {

            observationListView.setIsLock(true);
            OttoUtil.getInstance().postInMainThread(new OnCheerTopScroll());

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
