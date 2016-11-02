package com.mavlux.test.mvc.controller.activity.Drawer;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mavlux.test.R;

import java.util.ArrayList;

public class MyDrawerAdapter extends BaseAdapter {

    private ArrayList<MyDrawer> data = new ArrayList<>();
    private LayoutInflater inflater;

    private ImageView imageView;
    private static TextView textView;

    private View view;

    public MyDrawerAdapter(LayoutInflater inflater, ArrayList<MyDrawer> data){

        this.data = data;
        this.inflater = inflater;

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        view = convertView;

        if(view == null)
            view = inflater.inflate(R.layout.fragment_drawer_list_row, null);



        imageView = (ImageView)view.findViewById(R.id.imageButton_drawer_row);
        imageView.setBackgroundResource(data.get(position).getImage());

        switch (position){

            case 0:

                break;

        }

        textView = (TextView)view.findViewById(R.id.textView_drawer_row_text);
        textView.setText(data.get(position).getTitle());

        return view;
    }



}
