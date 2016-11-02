package com.mavlux.test.mvc.view.compare.info;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mavlux.test.R;


public class Header extends FrameLayout {

    private TextView textView_team_1, textView_team_2, textView_record_1, textView_record_2, textView_odds_1, textView_odds_2;
    private ImageView imageView_1, imageView_2;

    public Header(Context context) {
            this(context, null);
    }

    public Header(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.fragment_info_header, this);
        initView();
    }

    private void initView() {

        textView_team_1 = (TextView)findViewById(R.id.textView_info_header_name_1);
        textView_team_2 = (TextView)findViewById(R.id.textView_info_header_name_2);
        textView_record_1 = (TextView)findViewById(R.id.textView_info_header_record_1);
        textView_record_2 = (TextView)findViewById(R.id.textView_info_header_record_2);
        textView_odds_1 = (TextView)findViewById(R.id.textView_info_header_odds_1);
        textView_odds_2 = (TextView)findViewById(R.id.textView_info_header_odds_2);
        imageView_1 = (ImageView)findViewById(R.id.imageView_info_header_team_1);
        imageView_2 = (ImageView)findViewById(R.id.imageView_info_header_team_2);
    }

    public void setTeamname_1(String team1name){

        textView_team_1.setText(team1name);

    }

    public void setTeamname_2(String team2name){

        textView_team_2.setText(team2name);

    }

    public void setRecord_1(String record_1){

        textView_record_1.setText(record_1);

    }

    public void setRecord_2(String record_2){

        textView_record_2.setText(record_2);

    }

    public void setOdds_1(String odds_1){

        textView_odds_1.setText(odds_1);

    }

    public void setOdds_2(String odds_2){

        textView_odds_2.setText(odds_2);

    }

    public void setImageTeam_1(int image_1){

        imageView_1.setImageResource(R.drawable.game_3);
    }

    public void setImageTeam_2(int image_2){

        imageView_2.setImageResource(R.drawable.game_4);

    }

}
