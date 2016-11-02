package com.mavlux.test.mvc.controller.activity.Drawer;


public class MyDrawer {

    private int image;
    private String title;

    public MyDrawer(){

    }

    public static MyDrawer newInstance(){

        return new MyDrawer();
    }

    public MyDrawer setImage(int image) {

        this.image = image;
        return this;

    }

    public MyDrawer setTitle(String title){

        this.title = title;
        return this;

    }

    public int getImage(){

        return image;

    }

    public String getTitle(){

        return title;

    }
}
