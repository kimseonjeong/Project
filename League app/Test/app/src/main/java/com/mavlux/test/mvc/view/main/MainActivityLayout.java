package com.mavlux.test.mvc.view.main;


import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import com.mavlux.framework.toolbar.ToolbarLayout;
import com.mavlux.framework.view.ActivityLayout;
import com.mavlux.test.R;
import com.mavlux.test.mvc.controller.activity.Drawer.DrawerFragment;
import com.mavlux.test.mvc.controller.activity.main.MainActivity;



public class MainActivityLayout extends ActivityLayout<MainActivity, MainActivityLayoutListener> {

    private static ToolbarLayout toolbarLayout;
    private static DrawerLayout drawerLayout;

    public MainActivityLayout(MainActivity activity, MainActivityLayoutListener layoutListener) {

        super(activity, layoutListener);
    }

    @Override
    protected int getLayoutResId() {

        return R.layout.activity_main;
    }

    @Override
    protected void onLayoutInflated() {

        toolbarLayout = (ToolbarLayout) findViewById(R.id.toolbarLayout);
        getActivity().setSupportActionBar(toolbarLayout.getToolbar());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        final ActionBarDrawerToggle dtToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbarLayout.getToolbar(),0, 0);
        drawerLayout.setDrawerListener(dtToggle);
        drawerLayout.post(new Runnable() {

            @Override
            public void run() {

                dtToggle.syncState();
            }
        });

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_main, DrawerFragment.newInstance()).commit();


    }

    public static void setClose(){

        drawerLayout.closeDrawers();
    }

    public static void setTitle(String title){

        toolbarLayout.getToolbar().setTitle(title);
    }

}
