package com.mavlux.test.mvc.view.Drawer;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mavlux.framework.view.FragmentLayout;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.Drawer.DrawerFragment;
import com.mavlux.test.mvc.controller.activity.Drawer.MyDrawerAdapter;
import com.mavlux.test.mvc.controller.activity.Settings.SettingsFragment;
import com.mavlux.test.mvc.controller.activity.board.BoardFragment;
import com.mavlux.test.mvc.controller.activity.home.HomeFragment;
import com.mavlux.test.mvc.controller.activity.news.NewsFragment;
import com.mavlux.test.mvc.controller.activity.point.PointFragment;
import com.mavlux.test.mvc.controller.activity.rank.RankFragment;
import com.mavlux.test.mvc.view.main.MainActivityLayout;

public class DrawerFragmentLayout extends FragmentLayout<DrawerFragment, DrawerFragmentLayoutListener> implements AdapterView.OnItemClickListener, View.OnClickListener {

    private TextView textView_name, textView_email, textView_point, textView_combo, textView_ranking;
    private ListView listView;
    private ImageView imageView;

    public DrawerFragmentLayout(DrawerFragment fragment, DrawerFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container) {
        super(fragment, layoutListener, inflater, container);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_drawer;
    }

    @Override
    protected void onLayoutInflated() {
        initView();
    }

    private void initView() {

        textView_name = (TextView)findViewById(R.id.textView_drawer_name);
        textView_name.setText("김선정");

        textView_email = (TextView)findViewById(R.id.textView_drawer_email);
        textView_email.setText("tjswjd39@gmail.com");

        textView_point = (TextView)findViewById(R.id.textView_drawer_point);
        textView_point.setText("123,456점");

        textView_combo = (TextView)findViewById(R.id.textView_drawer_combo);
        textView_combo.setText("135콤보");

        textView_ranking = (TextView)findViewById(R.id.textView_drawer_ranking);
        textView_ranking.setText("14466위");

        listView = (ListView)findViewById(R.id.listView_drawer);

        imageView = (ImageView)findViewById(R.id.imageView_drawer_revise);
        imageView.setOnClickListener(this);

        listView.setAdapter(getLayoutListener().getAdapter());
        listView.setSelection(0);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){

            case 0:
                setReplace(HomeFragment.newInstance());
                setClose();
                setTitle("메인");
                break;

            case 1:
                setReplace(PointFragment.newInstance());
                setClose();
                setTitle("포인트 상점");
                break;

            case 2:
                setReplace(NewsFragment.newInstance());
                setClose();
                setTitle("오늘의 뉴스");
                break;

            case 3:
                setReplace(RankFragment.newInstance());
                setClose();
                setTitle("리그랭킹확인");
                break;

            case 4:
                setReplace(BoardFragment.newInstance());
                setClose();
                setTitle("통합게시판");
                break;

            case 5:
                setReplace(SettingsFragment.newInstance());
                setClose();
                setTitle("설정");
                break;
        }
    }


    public void setReplace(Fragment fragment){

        getFragment().getFragmentManager().beginTransaction().replace(R.id.fl_activity_main, fragment).commit();
    }

    public void setClose(){

        MainActivityLayout.setClose();
    }

    public void setTitle(String title){

        MainActivityLayout.setTitle(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_drawer_revise:
                break;
        }
    }

}
