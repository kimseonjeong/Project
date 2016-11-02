package com.mavlux.test.mvc.controller.activity.home;

public class MyList {

    private String game;
    private String team_1, team_2, score, date, channel;
    private int image_team_1, image_team_2;

    public MyList() {

    }

    public static MyList newInstance(){

        return new MyList();
    }

    public MyList setGame(String game){

        this.game = game;
        return this;

    }

    public MyList setTeam_1(String team_1){

        this.team_1 = team_1;
        return this;

    }

    public MyList setTeam_2(String team_2){

        this.team_2 = team_2;
        return this;

    }

    public MyList setScore(String score){

        this.score = score;
        return this;

    }

    public MyList setDate(String date){

        this.date = date;
        return this;

    }

    public MyList setChannel(String channel){

        this.channel = channel;
        return this;

    }

    public MyList setImage_Team_1(int image_team_1){

        this.image_team_1 = image_team_1;
        return this;

    }

    public MyList setImage_Team_2(int image_team_2){

        this.image_team_2 = image_team_2;
        return this;

    }

    public String getGame(){

        return game;
    }

    public String getTeam_1(){

        return team_1;

    }

    public String getTeam_2(){

        return team_2;

    }

    public String getScore(){

        return score;

    }

    public String getDate(){

        return date;

    }

    public String getChannel(){

        return channel;

    }

    public int getImage_team_1(){

        return image_team_1;

    }

    public int getImage_team_2(){

        return image_team_2;

    }
}
