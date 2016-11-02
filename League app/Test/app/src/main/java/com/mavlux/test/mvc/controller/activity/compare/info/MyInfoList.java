package com.mavlux.test.mvc.controller.activity.compare.info;

public class MyInfoList {

    private String position_1, name_1, gamename_1, w_1, l_1, kda_1;
    private String position_2, name_2, gamename_2, w_2, l_2, kda_2;

    public MyInfoList(){

    }

    public static MyInfoList newInstance(){

        return new MyInfoList();

    }

    public MyInfoList setPosition_1(String position_1){

        this.position_1 = position_1;
        return this;
    }

    public MyInfoList setName_1(String name_1){

        this.name_1 = name_1;
        return this;
    }

    public MyInfoList setGamename_1(String gamename_1){

        this.gamename_1 = gamename_1;
        return this;
    }

    public MyInfoList setW_1(String w_1){

        this.w_1 = w_1;
        return this;
    }

    public MyInfoList setL_1(String l_1){

        this.l_1 = l_1;
        return this;
    }

    public MyInfoList setKda_1(String kda_1){

        this.kda_1 = kda_1;
        return this;

    }

    public MyInfoList setPosition_2(String position_2){

        this.position_2 = position_2;
        return this;
    }

    public MyInfoList setName_2(String name_2){

        this.name_2 = name_2;
        return this;
    }

    public MyInfoList setGamename_2(String gamename_2){

        this.gamename_2 = gamename_2;
        return this;
    }

    public MyInfoList setW_2(String w_2){

        this.w_2 = w_2;
        return this;
    }

    public MyInfoList setL_2(String l_2){

        this.l_2 = l_2;
        return this;
    }

    public MyInfoList setKda_2(String kda_2){

        this.kda_2 = kda_2;
        return this;

    }

    public String getPosition_1(){

        return position_1;
    }

    public String getName_1(){

        return name_1;
    }

    public String getGamename_1(){

        return gamename_1;
    }

    public String getW_1(){

        return w_1;

    }

    public String getL_1(){

        return l_1;
    }

    public String getKda_1(){

        return kda_1;
    }

    public String getPosition_2(){

        return position_2;
    }

    public String getName_2(){

        return name_2;
    }

    public String getGamename_2(){

        return gamename_2;
    }

    public String getW_2(){

        return w_2;

    }

    public String getL_2(){

        return l_2;
    }

    public String getKda_2(){

        return kda_2;
    }
}
