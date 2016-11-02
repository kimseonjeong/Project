package com.mavlux.test.mvc.controller.activity.compare.info;

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
public class MyInfoListAdapter extends BaseAdapter {

    private ArrayList<MyInfoList> data = new ArrayList<>();
    private LayoutInflater inflater;

    private TextView textView_position_1, textView_name_1, textView_gamename_1, textView_w_1, textView_l_1, textView_kda_1;
    private TextView textView_position_2, textView_name_2, textView_gamename_2, textView_w_2, textView_l_2, textView_kda_2;

    public MyInfoListAdapter(LayoutInflater inflater, ArrayList<MyInfoList> data){

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
            convertView = inflater.inflate(R.layout.fragment_info_list_row, null);

        textView_position_1 = (TextView)convertView.findViewById(R.id.textView_info_row_position_1);
        textView_position_1.setText(data.get(position).getPosition_1());

        textView_name_1 = (TextView)convertView.findViewById(R.id.textView_info_row_name_1);
        textView_name_1.setText(data.get(position).getName_1());

        textView_gamename_1 = (TextView)convertView.findViewById(R.id.textView_info_row_gamename_1);
        textView_gamename_1.setText(data.get(position).getGamename_1());

        textView_w_1 = (TextView)convertView.findViewById(R.id.textView_info_row_w_1);
        textView_w_1.setText(data.get(position).getW_1());

        textView_l_1 = (TextView)convertView.findViewById(R.id.textView_info_row_l_1);
        textView_l_1.setText(data.get(position).getL_1());

        textView_kda_1 = (TextView)convertView.findViewById(R.id.textView_info_row_kda_1);
        textView_kda_1.setText(data.get(position).getKda_1());

        textView_position_2 = (TextView)convertView.findViewById(R.id.textView_info_row_position_2);
        textView_position_2.setText(data.get(position).getPosition_2());

        textView_name_2 = (TextView)convertView.findViewById(R.id.textView_info_row_name_2);
        textView_name_2.setText(data.get(position).getName_2());

        textView_gamename_2 = (TextView)convertView.findViewById(R.id.textView_info_row_gamename_2);
        textView_gamename_2.setText(data.get(position).getGamename_2());

        textView_w_2 = (TextView)convertView.findViewById(R.id.textView_info_row_w_2);
        textView_w_2.setText(data.get(position).getW_2());

        textView_l_2 = (TextView)convertView.findViewById(R.id.textView_info_row_l_2);
        textView_l_2.setText(data.get(position).getL_2());

        textView_kda_2 = (TextView)convertView.findViewById(R.id.textView_info_row_kda_2);
        textView_kda_2.setText(data.get(position).getKda_2());

        return convertView;
    }
}
