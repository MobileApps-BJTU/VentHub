package com.vxpai.entity;

import java.util.List;

/**
 * Created by 俊成 on 2015/3/28.
 */
public class ShitListItem {

    private UserData userData;
    private String content;
    private String time;
    private int praiseNum;
    private List<String> imgList;
    private List<CommentListItem> commentList;

    public ShitListItem(UserData userData, String content, String time, int praiseNum, List<String> imgList, List<CommentListItem> commentList) {
        this.userData = userData;
        this.content = content;
        this.time = time;
        this.praiseNum = praiseNum;
        this.imgList = imgList;
        this.commentList = commentList;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public List<CommentListItem> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListItem> commentList) {
        this.commentList = commentList;
    }
}
