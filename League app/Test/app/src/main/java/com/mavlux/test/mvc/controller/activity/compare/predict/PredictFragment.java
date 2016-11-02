package com.mavlux.test.mvc.controller.activity.compare.predict;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.mavlux.framework.controller.BaseFragment;
import com.mavlux.framework.util.OttoUtil;
import com.mavlux.test.R;
import com.mavlux.test.mvc.event.OnLastScroll;
import com.mavlux.test.mvc.view.compare.predict.PredictFragmentLayout;
import com.mavlux.test.mvc.view.compare.predict.PredictFragmentLayoutListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created by i3 on 2015-07-29.
 */
public class PredictFragment extends BaseFragment implements PredictFragmentLayoutListener {

    private PredictFragmentLayout fragmentLayout;
    private ArrayList<MyPredict> data = new ArrayList<>();
    private MyPredictAdapter adapter;

    private String POSITION = "MID";
    private String TEAM_1 = "T1";
    private String TEAM_2 = "MANU";
    private int IMAGE_1 = R.drawable.game_1;
    private int IMAGE_2 = R.drawable.game_2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        fragmentLayout = new PredictFragmentLayout(this, this, inflater, container);
        OttoUtil.getInstance().register(this);

        return fragmentLayout.getRootView();
    }

    public static PredictFragment newInstance(){

        return new PredictFragment();
    }

    public ListAdapter getAdapter(){

        AddData(POSITION,TEAM_1,TEAM_2,IMAGE_1,IMAGE_2);
        AddData(POSITION,TEAM_1,TEAM_2,IMAGE_1,IMAGE_2);
        AddData(POSITION,TEAM_1,TEAM_2,IMAGE_1,IMAGE_2);
        AddData(POSITION,TEAM_1,TEAM_2,IMAGE_1,IMAGE_2);
        AddData(POSITION,TEAM_1,TEAM_2,IMAGE_1,IMAGE_2);
        AddData(POSITION,TEAM_1,TEAM_2,IMAGE_1,IMAGE_2);
        AddData(POSITION,TEAM_1,TEAM_2,IMAGE_1,IMAGE_2);

        adapter = new MyPredictAdapter( LayoutInflater.from(getActivity()), data );
        return adapter;
    }

    private void AddData(String position, String team_1, String team_2, int image_1, int image_2) {
        data.add(MyPredict.newInstance().setPosition(position).setTeam1(team_1).setTeam2(team_2).setImageTeam1(image_1).setImageTeam2(image_2));
    }

    @Subscribe
    public void OnLastScroll( OnLastScroll onLastScroll){

        fragmentLayout.setListViewLock(false);
    }

    @Override
    public void onDestroyView() {

        OttoUtil.getInstance().unregister(this);
        super.onDestroyView();
    }
}
