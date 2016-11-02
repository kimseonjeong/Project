package com.mavlux.test.mvc.controller.activity.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.home.HomeFragment;

import com.mavlux.test.mvc.view.main.MainActivityLayout;
import com.mavlux.test.mvc.view.main.MainActivityLayoutListener;


public class MainActivity extends AppCompatActivity implements MainActivityLayoutListener {

    private MainActivityLayout mainActivityLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mainActivityLayout = new MainActivityLayout(this, this);
        setContentView(mainActivityLayout.getRootView());

        getSupportFragmentManager().beginTransaction().replace( R.id.fl_activity_main, HomeFragment.newInstance() ).commit();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }



}
