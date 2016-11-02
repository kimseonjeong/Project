package com.mavlux.test.mvc.controller.activity.compare.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.mavlux.framework.controller.BaseFragment;
import com.mavlux.framework.util.OttoUtil;
import com.mavlux.test.mvc.event.OnLastScroll;
import com.mavlux.test.mvc.event.OnPredictTopScroll;
import com.mavlux.test.mvc.view.compare.info.InfoFragmentLayout;
import com.mavlux.test.mvc.view.compare.info.InfoFragmentLayoutListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created by i3 on 2015-07-29.
 */
public class InfoFragment extends BaseFragment implements InfoFragmentLayoutListener {

    private InfoFragmentLayout fragmentLayout;
    private ArrayList<MyInfoList> data = new ArrayList<>();

    private String POSITION_1 = "탑";
    private String NAME_1 = "홍길동";
    private String GAMENAME_1 = "Shy";
    private String W_1 = "123";
    private String L_1 = "456";
    private String KDA_1 = "789%";

    private String POSITION_2 = "미드";
    private String NAME_2 = "고길동";
    private String GAMENAME_2 = "MeMe";
    private String W_2 = "789";
    private String L_2 = "456";
    private String KDA_2 = "123%";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentLayout = new InfoFragmentLayout(this, this, inflater, container);
        OttoUtil.getInstance().register(this);

        return fragmentLayout.getRootView();
    }

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public ListAdapter getAdapter() {

        AddData(POSITION_1, NAME_1, GAMENAME_1, W_1, L_1, KDA_1, POSITION_2, NAME_2, GAMENAME_2, W_2, L_2, KDA_2);
        AddData(POSITION_1, NAME_1, GAMENAME_1, W_1, L_1, KDA_1, POSITION_2, NAME_2, GAMENAME_2, W_2, L_2, KDA_2);
        AddData(POSITION_1, NAME_1, GAMENAME_1, W_1, L_1, KDA_1, POSITION_2, NAME_2, GAMENAME_2, W_2, L_2, KDA_2);
        AddData(POSITION_1, NAME_1, GAMENAME_1, W_1, L_1, KDA_1, POSITION_2, NAME_2, GAMENAME_2, W_2, L_2, KDA_2);
        AddData(POSITION_1, NAME_1, GAMENAME_1, W_1, L_1, KDA_1, POSITION_2, NAME_2, GAMENAME_2, W_2, L_2, KDA_2);
        AddData(POSITION_1, NAME_1, GAMENAME_1, W_1, L_1, KDA_1, POSITION_2, NAME_2, GAMENAME_2, W_2, L_2, KDA_2);
        AddData(POSITION_1, NAME_1, GAMENAME_1, W_1, L_1, KDA_1, POSITION_2, NAME_2, GAMENAME_2, W_2, L_2, KDA_2);
        AddData(POSITION_1, NAME_1, GAMENAME_1, W_1, L_1, KDA_1, POSITION_2, NAME_2, GAMENAME_2, W_2, L_2, KDA_2);
        AddData(POSITION_1, NAME_1, GAMENAME_1, W_1, L_1, KDA_1, POSITION_2, NAME_2, GAMENAME_2, W_2, L_2, KDA_2);
        AddData(POSITION_1, NAME_1, GAMENAME_1, W_1, L_1, KDA_1, POSITION_2, NAME_2, GAMENAME_2, W_2, L_2, KDA_2);
        AddData(POSITION_1, NAME_1, GAMENAME_1, W_1, L_1, KDA_1, POSITION_2, NAME_2, GAMENAME_2, W_2, L_2, KDA_2);
        AddData(POSITION_1, NAME_1, GAMENAME_1, W_1, L_1, KDA_1, POSITION_2, NAME_2, GAMENAME_2, W_2, L_2, KDA_2);
        AddData(POSITION_1, NAME_1, GAMENAME_1, W_1, L_1, KDA_1, POSITION_2, NAME_2, GAMENAME_2, W_2, L_2, KDA_2);


        MyInfoListAdapter adapter = new MyInfoListAdapter(LayoutInflater.from(getActivity()), data);
        return adapter;
    }

    public void AddData(String position_1, String name_1, String gamename_1, String w_1, String l_1, String kda_1,
                        String position_2, String name_2, String gamename_2, String w_2, String l_2, String kda_2) {
        data.add(MyInfoList.newInstance().setPosition_1(position_1).setName_1(name_1).setGamename_1(gamename_1).setW_1(w_1).setL_1(l_1).setKda_1(kda_1)
                .setPosition_2(position_2).setName_2(name_2).setGamename_2(gamename_2).setW_2(w_2).setL_2(l_2).setKda_2(kda_2));
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
