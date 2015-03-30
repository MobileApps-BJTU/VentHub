package com.vxpai.entity;

/**
 * Created by 俊成 on 2015/3/30.
 */
public class ReplyListItem {

    private UserData replyFrom;
    private UserData replyTo;
    private String replyDetail;
    private String replyTime;

    public ReplyListItem(UserData replyFrom, UserData replyTo, String replyDetail) {
        this.replyFrom = replyFrom;
        this.replyTo = replyTo;
        this.replyDetail = replyDetail;
    }

    public UserData getReplyFrom() {
        return replyFrom;
    }

    public void setReplyFrom(UserData replyFrom) {
        this.replyFrom = replyFrom;
    }

    public UserData getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(UserData replyTo) {
        this.replyTo = replyTo;
    }

    public String getReplyDetail() {
        return replyDetail;
    }

    public void setReplyDetail(String replyDetail) {
        this.replyDetail = replyDetail;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }
}
