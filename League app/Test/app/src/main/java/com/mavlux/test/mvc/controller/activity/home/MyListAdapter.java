package com.mavlux.test.mvc.controller.activity.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mavlux.test.R;

import java.util.ArrayList;

/**
 * Created by i3 on 2015-07-28.
 */
public class MyListAdapter extends BaseAdapter {

    private ArrayList<MyList> data;
    private LayoutInflater inflater;
    private Context context;
    private TextView textView_team_1, textView_team_2, textView_score, textView_date, textView_channel;
    private ImageView imageView_team_1, imageView_team_2;

    public MyListAdapter(LayoutInflater inflater, ArrayList<MyList> data){

        this.data = data;
        this.inflater = inflater;
        context = (Context)inflater.getContext();
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

        if(convertView == null)
            convertView = inflater.inflate(R.layout.fragment_viewpager_list_row, null);

        textView_team_1 = (TextView)convertView.findViewById(R.id.textView_team_1);
        textView_team_1.setText(data.get(position).getTeam_1());

        textView_team_2 = (TextView)convertView.findViewById(R.id.textView_team_2);
        textView_team_2.setText(data.get(position).getTeam_2());

        textView_score = (TextView)convertView.findViewById(R.id.textView_score);
        textView_score.setText(data.get(position).getScore());

        textView_date = (TextView)convertView.findViewById(R.id.textView_date);
        textView_date.setText(data.get(position).getDate());

        textView_channel = (TextView)convertView.findViewById(R.id.textView_channel);
        textView_channel.setText(data.get(position).getChannel());

        imageView_team_1 = (ImageView)convertView.findViewById(R.id.imageView_team_1);
        imageView_team_1.setImageResource(data.get(position).getImage_team_1());

        imageView_team_2 = (ImageView)convertView.findViewById(R.id.imageView_team_2);
        imageView_team_2.setImageResource(data.get(position).getImage_team_2());

        return convertView;
    }
}
