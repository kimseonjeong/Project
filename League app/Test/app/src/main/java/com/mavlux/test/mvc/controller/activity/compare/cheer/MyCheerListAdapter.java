package com.mavlux.test.mvc.controller.activity.compare.cheer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mavlux.test.R;

import java.util.ArrayList;

/**
 * Created by i3 on 2015-07-29.
 */
public class MyCheerListAdapter extends BaseAdapter {

    private ArrayList<MyCheerList> data = new ArrayList<>();
    private LayoutInflater inflater;

    private TextView textView_content, textView_name, textView_date, textView_time;

    public MyCheerListAdapter(LayoutInflater inflater, ArrayList<MyCheerList> data) {

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


        if (data.get(position).getValue() == 1) {

            if (convertView == null)
                convertView = inflater.inflate(R.layout.fragment_cheer_list_left_row, null);

            textView_content = (TextView) convertView.findViewById(R.id.textView_cheer_left_content);
            textView_content.setText(data.get(position).getContent());

            textView_name = (TextView) convertView.findViewById(R.id.textView_cheer_left_name);
            textView_name.setText(data.get(position).getName());

            textView_date = (TextView) convertView.findViewById(R.id.textView_cheer_left_date);
            textView_date.setText(data.get(position).getDate());

            textView_time = (TextView) convertView.findViewById(R.id.textView_cheer_left_time);
            textView_time.setText(data.get(position).getTime());

        } else if (data.get(position).getValue() == 2) {

            if (convertView == null)
                convertView = inflater.inflate(R.layout.fragment_cheer_list_right_row, null);

            textView_content = (TextView) convertView.findViewById(R.id.textView_cheer_right_content);
            textView_content.setText(data.get(position).getContent());

            textView_name = (TextView) convertView.findViewById(R.id.textView_cheer_right_name);
            textView_name.setText(data.get(position).getName());

            textView_date = (TextView) convertView.findViewById(R.id.textView_cheer_right_date);
            textView_date.setText(data.get(position).getDate());

            textView_time = (TextView) convertView.findViewById(R.id.textView_cheer_right_time);
            textView_time.setText(data.get(position).getTime());

        }


        return convertView;
    }
}
