package com.mavlux.test.mvc.view.board;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mavlux.framework.view.FragmentLayout;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.board.BoardFragment;

public class BoardFragmentLayout extends FragmentLayout<BoardFragment, BoardFragmentLayoutListener> {

    public BoardFragmentLayout(BoardFragment fragment, BoardFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container) {
        super(fragment, layoutListener, inflater, container);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_board;
    }

    @Override
    protected void onLayoutInflated() {

    }
}
