package com.mavlux.test.mvc.view.compare.info;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mavlux.test.R;


public class Footer extends FrameLayout {

    private TextView textView_team_1, textView_team_2, textView_play, textView_win, textView_lose, textView_odds;

    public Footer(Context context) {

        this(context, null);
    }

    public Footer(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.fragment_info_footer, this);
        initView();
    }

    private void initView() {

        textView_team_1 = (TextView)findViewById(R.id.textView_info_footer_team_1);
        textView_team_2 = (TextView)findViewById(R.id.textView_info_footer_team_2);
        textView_play = (TextView)findViewById(R.id.textView_info_footer_play);
        textView_win = (TextView)findViewById(R.id.textView_info_footer_win);
        textView_lose = (TextView)findViewById(R.id.textView_info_footer_lose);
        textView_odds = (TextView)findViewById(R.id.textView_info_footer_odds);


    }

    public void setTeamname_1(String team1name){

        textView_team_1.setText(team1name);

    }

    public void setTeamname_2(String team2name){

        textView_team_2.setText(team2name);

    }

    public void setPlay(int play){

        textView_play.setText(String.valueOf(play));

    }

    public void setWin(int win){

        textView_win.setText(String.valueOf(win));

    }

    public void setLose(int lose){

        textView_lose.setText(String.valueOf(lose));

    }

    public void setOdds(String odds){

        textView_odds.setText(odds);

    }


}
