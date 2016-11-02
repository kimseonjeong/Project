package com.mavlux.test.mvc.controller.activity.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mavlux.framework.controller.BaseFragment;
import com.mavlux.test.mvc.view.board.BoardFragmentLayout;
import com.mavlux.test.mvc.view.board.BoardFragmentLayoutListener;

/**
 * Created by i3 on 2015-07-27.
 */
public class BoardFragment extends BaseFragment implements BoardFragmentLayoutListener {

    private BoardFragmentLayout fragmentLayout;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        fragmentLayout = new BoardFragmentLayout(this,this,inflater,container);

        return fragmentLayout.getRootView();
    }

    public static BoardFragment newInstance(){
        return new BoardFragment();
    }
}
