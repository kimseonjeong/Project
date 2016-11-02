package com.mavlux.test.mvc.controller.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mavlux.framework.controller.BaseFragment;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.compare.CompareFragment;

import java.util.ArrayList;


public class HomeListFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private View view;
    private TextView textView_pre, textView_now, textView_next;
    private ListView listView;
    private int position;

    private ArrayList<MyList> data = new ArrayList<>();
    private MyListAdapter mylistadapter;

    private String TEAM_1 = "T1";
    private String TEAM_2 = "OHOH";
    private String SCORE = "2:1";
    private String DATE = "7/20 월 12:30";
    private String CHANNEL = "Afreeca TV / Inven";
    private int IMAGE_1 = R.drawable.game_1;
    private int IMAGE_2 = R.drawable.game_2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_viewpager_list, container, false);

        initView();

        mylistadapter = new MyListAdapter(getLayoutInflater(savedInstanceState),data);


        AddData(TEAM_1, TEAM_2,SCORE,DATE,CHANNEL,IMAGE_1,IMAGE_2);
        AddData(TEAM_1, TEAM_2,SCORE,DATE,CHANNEL,IMAGE_1,IMAGE_2);
        AddData(TEAM_1, TEAM_2,SCORE,DATE,CHANNEL,IMAGE_1,IMAGE_2);
        AddData(TEAM_1, TEAM_2,SCORE,DATE,CHANNEL,IMAGE_1,IMAGE_2);

        listView.setAdapter(mylistadapter);
        listView.setOnItemClickListener(this);


        return view;
    }

    private void AddData(String team_1, String team_2, String score, String date, String channel, int image_1, int image_2) {

        data.add(MyList.newInstance().setTeam_1(team_1).setTeam_2(team_2).setScore(score).setDate(date).setChannel(channel).setImage_Team_1(image_1).setImage_Team_2(image_2));

    }

    public void initView(){

        textView_pre = (TextView) view.findViewById(R.id.textView_home_date_pre);
        textView_now = (TextView) view.findViewById(R.id.textView_home_date_now);
        textView_next = (TextView) view.findViewById(R.id.textView_home_date_next);

        listView = (ListView) view.findViewById(R.id.listView_home_list);

    }

    public Fragment setPosition(int position){

        this.position = position;
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        getFragmentManager().beginTransaction().replace(R.id.fl_activity_main, CompareFragment.newInstance()).commit();
    }

    public static HomeListFragment newInstance(){

        return new HomeListFragment();
    }


}
