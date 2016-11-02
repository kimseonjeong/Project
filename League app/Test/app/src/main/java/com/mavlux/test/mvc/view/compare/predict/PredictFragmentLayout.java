package com.mavlux.test.mvc.view.compare.predict;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.mavlux.framework.util.OttoUtil;
import com.mavlux.framework.view.FragmentLayout;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.compare.predict.PredictFragment;
import com.mavlux.test.mvc.event.OnPredictTopScroll;
import com.mavlux.widget.ObservationListView;

public class PredictFragmentLayout extends FragmentLayout<PredictFragment, PredictFragmentLayoutListener> implements AbsListView.OnScrollListener {

    private static ObservationListView listView;
    private Header header;
    private boolean topItemVisibleFlag = false;

    public PredictFragmentLayout(PredictFragment fragment, PredictFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container) {
        super(fragment, layoutListener, inflater, container);
    }

    @Override
    protected int getLayoutResId() {

        return R.layout.fragment_predict;
    }

    @Override
    protected void onLayoutInflated() {

        initView();
    }

    private void initView() {

        listView = (ObservationListView) findViewById(R.id.listView_predict);
        listView.setOnScrollListener(this);
        listView.setIsLock(true);

        header = new Header(getActivity());
        header.setName_1("T1");
        header.setName_2("SBENU");
        header.setRecord_1("12전34승56패");
        header.setRecord_2("56전34승12패");
        header.setOdds_1("승률 12%");
        header.setOdds_2("승률 34%");
        header.setImage_1(R.drawable.game_3);
        header.setImage_2(R.drawable.game_4);
        header.setPercent_1("50%");
        header.setPercent_2("50%");


        listView.addHeaderView(header);


        listView.setAdapter(getLayoutListener().getAdapter());

    }

    public void setListViewLock(boolean isLock) {

        listView.setIsLock(isLock);
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if( scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && header.getTop()==0 ){

            listView.setIsLock(true);
            OttoUtil.getInstance().postInMainThread(new OnPredictTopScroll());

        }
    }

}
