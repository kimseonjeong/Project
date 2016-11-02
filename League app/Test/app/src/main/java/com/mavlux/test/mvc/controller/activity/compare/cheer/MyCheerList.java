package com.mavlux.test.mvc.controller.activity.compare.cheer;

/**
 * Created by i3 on 2015-07-29.
 */
public class MyCheerList {

    private String content, name, date, time;
    private int value;

    public MyCheerList(){

    }

    public static MyCheerList newInstance(){

        return new MyCheerList();
    }

    public MyCheerList setValue(int value){

        this.value = value;
        return this;
    }

    public MyCheerList setContent(String content){

        this.content = content;
        return this;
    }

    public MyCheerList setName(String name){

        this.name = name;
        return this;

    }

    public MyCheerList setDate(String date){

        this.date = date;
        return this;

    }

    public MyCheerList setTime(String time){

        this.time = time;
        return this;

    }

    public int getValue(){

        return value;
    }

    public String getContent(){

        return content;
    }

    public String getName(){

        return name;
    }

    public String getDate(){

        return date;
    }

    public String getTime(){

        return time;
    }
}
