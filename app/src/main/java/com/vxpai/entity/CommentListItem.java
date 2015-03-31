package com.vxpai.entity;

import java.util.List;

/**
 * Created by 俊成 on 2015/3/30.
 */
public class CommentListItem {

    private UserData userData;
    private String commentTime;
    private String commentDetail;
    private List<ReplyListItem> replyList;

    public CommentListItem(UserData userData, String commentDetail, List<ReplyListItem> replyList, String commentTime) {
        this.userData = userData;
        this.commentDetail = commentDetail;
        this.replyList = replyList;
        this.commentTime = commentTime;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentDetail() {
        return commentDetail;
    }

    public void setCommentDetail(String commentDetail) {
        this.commentDetail = commentDetail;
    }

    public List<ReplyListItem> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyListItem> replyList) {
        this.replyList = replyList;
    }
}
