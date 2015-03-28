package com.vxpai.entity;

/**
 * Created by 俊成 on 2015/3/27.
 */
public class User {

    private String imagePath;
    private String username;

    public User(){

    }

    public User(String imagePath, String username){
        this.imagePath = imagePath;
        this.username = username;
    }

    public String getImagePath(){ return imagePath; }

    public void setImagePath(String imagePath){ this.imagePath = imagePath; }

    public String getUsername(){ return username; }

    public void setUsername(String username){ this.username = username; }
}
