package com.mavlux.test.mvc.controller.activity.compare.predict;

/**
 * Created by i3 on 2015-07-30.
 */
public class MyPredict {

    private String team_1, team_2, position;
    private int image_team_1, image_team_2;

    public MyPredict(){

    }

    public static MyPredict newInstance(){

        return new MyPredict();

    }

    public MyPredict setTeam1(String team_1){

        this.team_1 = team_1;
        return this;
    }

    public MyPredict setTeam2(String team_2){

        this.team_2 = team_2;
        return  this;

    }

    public MyPredict setPosition(String position){

        this.position = position;
        return this;

    }

    public MyPredict setImageTeam1(int image_team_1){

        this.image_team_1 = image_team_1;
        return this;

    }

    public MyPredict setImageTeam2(int image_team_2){

        this.image_team_2 = image_team_2;
        return this;

    }

    public String getTeam_1(){

        return team_1;
    }

    public String getTeam_2(){

        return team_2;

    }

    public String getPosition(){

        return position;

    }

    public int getImage_team_1(){

        return image_team_1;
    }

    public int getImage_team_2(){

        return image_team_2;

    }
}
