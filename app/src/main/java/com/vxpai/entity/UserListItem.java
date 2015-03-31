package com.vxpai.entity;

/**
 * Created by 俊成 on 2015/3/27.
 */
public class UserListItem {

    private UserData userData;

    public UserListItem(UserData userData) {
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
