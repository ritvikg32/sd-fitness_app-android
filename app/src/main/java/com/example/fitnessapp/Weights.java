package com.example.fitnessapp;

import android.app.Application;

public class Weights extends Application {
    private String name;
    private String image;
    private String video;
    private static Weights instance;

    public Weights(String name, String image, String video) {
        this.name = name;
        this.image = image;
        this.video = video;
    }

    public static Weights getInstance(){
    if(instance==null) instance=new Weights();

    return instance;
    }

    public Weights() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.image = image;
    }

    public String getvideo() {
        return video;
    }

    public void setvideo(String video) {
        this.video = video;
    }
}
