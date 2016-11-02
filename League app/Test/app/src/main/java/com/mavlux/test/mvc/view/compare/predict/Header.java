package com.mavlux.test.mvc.view.compare.predict;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mavlux.test.R;

/**
 * Created by i3 on 2015-07-30.
 */
public class Header extends FrameLayout implements View.OnClickListener {

    private TextView textView_name_1, textView_name_2, textView_record_1, textView_record_2, textView_odds_1, textView_odds_2, textView_percent_1, textView_percent_2;
    private ImageView imageView_1, imageView_2;
    private Button button_1, button_2;
    private ProgressBar progressBar;

    public Header(Context context) {
        this(context, null);
    }

    public Header(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.fragment_predict_header, this);
        iniView();
    }

    private void iniView() {

        progressBar = (ProgressBar) findViewById(R.id.progressBar_predict);
        progressBar.setVisibility(View.VISIBLE);
        textView_name_1 = (TextView) findViewById(R.id.textView_predict_name_1);
        textView_name_2 = (TextView) findViewById(R.id.textView_predict_name_2);
        textView_record_1 = (TextView) findViewById(R.id.textView_predict_record_1);
        textView_record_2 = (TextView) findViewById(R.id.textView_predict_record_2);
        textView_odds_1 = (TextView) findViewById(R.id.textView_predict_odds_1);
        textView_odds_2 = (TextView) findViewById(R.id.textView_predict_odds_2);
        imageView_1 = (ImageView) findViewById(R.id.imageView_predict_1);
        imageView_2 = (ImageView) findViewById(R.id.imageView_predict_2);
        button_1 = (Button) findViewById(R.id.button_predict_1);
        button_1.setOnClickListener(this);
        button_2 = (Button) findViewById(R.id.button_predict_2);
        button_2.setOnClickListener(this);
        textView_percent_1 = (TextView) findViewById(R.id.textView_predict_percent_1);
        textView_percent_2 = (TextView) findViewById(R.id.textView_predict_percent_2);

    }

    public void setName_1(String name_1) {

        textView_name_1.setText(name_1);

    }

    public void setName_2(String name_2) {

        textView_name_2.setText(name_2);

    }

    public void setRecord_1(String record_1) {

        textView_record_1.setText(record_1);

    }

    public void setRecord_2(String record_2) {

        textView_record_2.setText(record_2);

    }

    public void setOdds_1(String odds_1) {

        textView_odds_1.setText(odds_1);

    }

    public void setOdds_2(String odds_2) {

        textView_odds_2.setText(odds_2);

    }

    public void setImage_1(int image_1) {

        imageView_1.setImageResource(image_1);

    }

    public void setImage_2(int image_2) {

        imageView_2.setImageResource(image_2);

    }

    public void setPercent_1(String percent_1) {

        textView_percent_1.setText(percent_1);

    }

    public void setPercent_2(String percent_2) {

        textView_percent_2.setText(percent_2);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_predict_1:
                setBetting_1();
                break;

            case R.id.button_predict_2:
                setBetting_2();
                break;
        }
    }

    private void setBetting_1() {

        progressBar.incrementProgressBy(1);
        setPercent_1(progressBar.getProgress() + "%");
        setPercent_2(progressBar.getMax() - progressBar.getProgress() + "%");
    }

    private void setBetting_2() {

        progressBar.incrementProgressBy(-1);
        setPercent_1(progressBar.getProgress() + "%");
        setPercent_2(progressBar.getMax() - progressBar.getProgress() + "%");
    }
}
