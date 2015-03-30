package com.vxpai.entity;

/**
 * Created by 俊成 on 2015/3/29.
 */
public class FriendListItem {

    private String mImagePath;
    private String mNickName;

    public FriendListItem(String mImagePath, String mNickName){
        this.mImagePath = mImagePath;
        this.mNickName = mNickName;
    }

    public String getmImagePath() {
        return mImagePath;
    }

    public void setmImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    public String getmNickName() {
        return mNickName;
    }

    public void setmNickName(String mNickName) {
        this.mNickName = mNickName;
    }
}
