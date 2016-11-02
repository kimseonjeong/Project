package com.mavlux.test.mvc.controller.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mavlux.framework.controller.BaseFragment;
import com.mavlux.test.R;

import java.util.ArrayList;

/**
 * Created by i3 on 2015-07-27.
 */
public class HomeImageFragment extends BaseFragment {

    private View view;
    private ImageView imageView_background;
    private int position;

    private ArrayList<MyList> data = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_viewpager_game, container, false);
        imageView_background = (ImageView) view.findViewById(R.id.imageView_background);

        setImage();
        setList();
        return view;
    }

    private void setImage() {

        switch (position) {

            case 0:

                imageView_background.setImageResource(R.drawable.totalview_image);
                break;

            case 1:

                imageView_background.setImageResource(R.drawable.lolview_image);
                break;

            case 2:

                imageView_background.setImageResource(R.drawable.stoneview_image);
                break;

            case 3:

                imageView_background.setImageResource(R.drawable.starview_image);
                break;

            case 4:

                imageView_background.setImageResource(R.drawable.heroview_image);
                break;
        }
    }

    public void setList(){

        switch (position){
            case 0:
                data.add(MyList.newInstance().setGame("TOTAL"));
                break;

            case 1:
                data.add(MyList.newInstance().setGame("LOL"));
                break;

            case 2:
                data.add(MyList.newInstance().setGame("STONE"));
                break;

            case 3:
                data.add(MyList.newInstance().setGame("STAR"));
                break;

            case 4:
                data.add(MyList.newInstance().setGame("HERO"));
                break;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public Fragment setPosition(int position) {

        this.position = position;
        return this;
    }

    public static HomeImageFragment newInstance() {

        return new HomeImageFragment();
    }

}
