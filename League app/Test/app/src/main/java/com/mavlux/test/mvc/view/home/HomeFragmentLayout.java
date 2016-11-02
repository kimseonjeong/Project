package com.mavlux.test.mvc.view.home;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mavlux.framework.view.FragmentLayout;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.home.HomeFragment;

public class HomeFragmentLayout extends FragmentLayout<HomeFragment, HomeFragmentLayoutListener> implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager viewPager_games, viewPager_list;
    private ImageView imageView_left, imageView_right;


    public HomeFragmentLayout(HomeFragment fragment, HomeFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container) {
        super(fragment, layoutListener, inflater, container);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onLayoutInflated() {

        initView();
    }

    public void initView(){

        viewPager_games = (ViewPager) findViewById(R.id.viewPager_games);
        viewPager_games.setAdapter(getLayoutListener().getGameAdapter());
        viewPager_games.addOnPageChangeListener(this);

        viewPager_list = (ViewPager) findViewById(R.id.viewPager_list);
        viewPager_list.setAdapter(getLayoutListener().getListAdapter());
        viewPager_list.addOnPageChangeListener(this);

        imageView_left = (ImageView) findViewById(R.id.imageView_left);
        imageView_left.setOnClickListener(this);

        imageView_right = (ImageView)findViewById(R.id.imageView_right);
        imageView_right.setOnClickListener(this);


    }



    public void nextPage(){

        int currentPage = viewPager_games.getCurrentItem();

        int nextPage = currentPage + 1;
        if ( nextPage > 5 )
            nextPage = 5;

        viewPager_games.setCurrentItem(nextPage);
    }

    public void prePage(){

        int currentPage = viewPager_games.getCurrentItem();

        int prePage = currentPage - 1;
        if( prePage < 0 )
            prePage = 0;

        viewPager_games.setCurrentItem(prePage);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        ////글씨를 작게하고 크게하고
    }

    @Override
    public void onPageSelected(int position){


    }

    @Override
    public void onPageScrollStateChanged(int state) {
        ///게임이 바꼈으니깐 리스트를 다시 가져옴

        switch (getRootView().getId()){

            case R.id.viewPager_games:
                viewPager_list.setCurrentItem(0);
                break;

            case R.id.viewPager_list:
                break;

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.imageView_left:
                prePage();
                break;

            case R.id.imageView_right:
                nextPage();
                break;
        }
    }


}
