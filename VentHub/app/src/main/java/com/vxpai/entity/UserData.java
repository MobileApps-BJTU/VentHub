package com.vxpai.entity;

/**
 * Created by 俊成 on 2015/3/30.
 */
public class UserData {

    private String email;
    private String username;
    private String password;
    private String dob;
    private String province;
    private String gender;
    private String avatar;

    public UserData(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.dob = null;
        this.province = null;
        this.gender = null;
        this.avatar = null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
