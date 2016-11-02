package com.mavlux.test.mvc.view.compare.info;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.mavlux.framework.util.OttoUtil;
import com.mavlux.framework.view.FragmentLayout;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.compare.info.InfoFragment;
import com.mavlux.test.mvc.event.OnInfoTopScroll;
import com.mavlux.test.mvc.event.OnPredictTopScroll;
import com.mavlux.widget.ObservationListView;

public class InfoFragmentLayout extends FragmentLayout<InfoFragment, InfoFragmentLayoutListener> implements AbsListView.OnScrollListener {

    private ObservationListView listView;
    private Footer footer;
    private Header header;

    private String NAME_1 = "T1";
    private String NAME_2 = "SBENU";

    public InfoFragmentLayout(InfoFragment fragment, InfoFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container) {
        super(fragment, layoutListener, inflater, container);
    }

    @Override
    protected int getLayoutResId() {

        return R.layout.fragment_info;
    }

    @Override
    protected void onLayoutInflated() {

        initView();
    }

    private void initView() {

        listView = (ObservationListView)findViewById(R.id.listView_info);
        listView.setOnScrollListener(this);
        listView.setIsLock(true);

        header = new Header(getActivity());
        header.setImageTeam_1(R.drawable.game_3);
        header.setImageTeam_2(R.drawable.game_4);
        header.setTeamname_1("T1");
        header.setTeamname_2("SBNU");
        header.setRecord_1("65전43승21패");
        header.setRecord_2("12전34승56패");
        header.setOdds_1("승률 12%");
        header.setOdds_2("승률 34%");

        footer = new Footer(getActivity());
        footer.setTeamname_1("T1");
        footer.setTeamname_2("SBENU");
        footer.setPlay(12);
        footer.setWin(34);
        footer.setLose(56);
        footer.setOdds("0.789");


        listView.addHeaderView(header);
        listView.addFooterView(footer);


        listView.setAdapter(getLayoutListener().getAdapter());


    }

    public void setListViewLock(boolean isLock){

        listView.setIsLock(isLock);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if( scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && header.getTop()==0 ){

            listView.setIsLock(true);
            OttoUtil.getInstance().postInMainThread(new OnInfoTopScroll());

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
