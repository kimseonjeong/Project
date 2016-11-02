package com.mavlux.test.mvc.controller.activity.Drawer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.mavlux.framework.controller.BaseFragment;
import com.mavlux.test.R;
import com.mavlux.test.mvc.view.Drawer.DrawerFragmentLayout;
import com.mavlux.test.mvc.view.Drawer.DrawerFragmentLayoutListener;

import java.util.ArrayList;


public class DrawerFragment extends BaseFragment implements DrawerFragmentLayoutListener {

    private DrawerFragmentLayout fragmentLayout;
    private ArrayList<MyDrawer> data = new ArrayList<>();
    private MyDrawerAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        fragmentLayout = new DrawerFragmentLayout(this, this, inflater, container);

        return fragmentLayout.getRootView();
    }

    public static DrawerFragment newInstance(){

        return new DrawerFragment();

    }

    @Override
    public ListAdapter getAdapter() {

        data.add(MyDrawer.newInstance().setImage(R.drawable.drawer_main_select).setTitle("메인"));
        data.add(MyDrawer.newInstance().setImage(R.drawable.drawer_point_select).setTitle("포인트 상점"));
        data.add(MyDrawer.newInstance().setImage(R.drawable.drawer_news_select).setTitle("오늘의 뉴스"));
        data.add(MyDrawer.newInstance().setImage(R.drawable.drawer_rank_select).setTitle("리그랭킹확인"));
        data.add(MyDrawer.newInstance().setImage(R.drawable.drawer_board_select).setTitle("통합게시판"));
        data.add(MyDrawer.newInstance().setImage(R.drawable.drawer_settings_select).setTitle("설정"));

        adapter = new MyDrawerAdapter(LayoutInflater.from(getActivity()), data);
        return adapter;
    }




}
