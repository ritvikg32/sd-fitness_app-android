package com.example.fitnessapp;

import android.app.Application;

public class Weights{
    private String name;
    private String image;
    private String video;
    private static Weights instance;

    public Weights() { }

    public Weights(String name, String image, String video) {
        this.name = name;
        this.image = image;
        this.video = video;
    }

//    public static Weights getInstance(){
//    if(instance==null) instance=new Weights();
//
//    return instance;
//    }
//


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
