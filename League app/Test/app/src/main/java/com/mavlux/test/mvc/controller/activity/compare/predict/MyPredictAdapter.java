package com.mavlux.test.mvc.controller.activity.compare.predict;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mavlux.test.R;

import java.util.ArrayList;

/**
 * Created by i3 on 2015-07-30.
 */
public class MyPredictAdapter extends BaseAdapter {

    private ArrayList<MyPredict> data = new ArrayList<>();
    private LayoutInflater inflater;

    private TextView textView_team_1, textView_team_2, textView_position;
    private ImageView imageView_team_1, imageView_team_2;

    public MyPredictAdapter(LayoutInflater inflater, ArrayList<MyPredict> data){

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

        if(convertView == null)
            convertView = inflater.inflate(R.layout.fragment_predict_list_row, null);

        textView_position = (TextView)convertView.findViewById(R.id.textView_predict_row_position);
        textView_position.setText(data.get(position).getPosition());

        textView_team_1 = (TextView)convertView.findViewById(R.id.textView_predict_row_1);
        textView_team_1.setText(data.get(position).getTeam_1());

        textView_team_2 = (TextView)convertView.findViewById(R.id.textView_predict_row_2);
        textView_team_2.setText(data.get(position).getTeam_2());

        imageView_team_1 = (ImageView)convertView.findViewById(R.id.imageView_predict_row_1);
        imageView_team_1.setImageResource(R.drawable.game_3);

        imageView_team_2 = (ImageView)convertView.findViewById(R.id.imageView_predict_row_2);
        imageView_team_2.setImageResource(R.drawable.game_4);

        return convertView;
    }
}
