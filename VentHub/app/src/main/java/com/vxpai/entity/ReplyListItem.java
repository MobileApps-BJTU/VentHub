package com.vxpai.entity;

/**
 * Created by 俊成 on 2015/3/30.
 */
public class ReplyListItem {

    private String replyFrom;
    private String replyTo;
    private String replyDetail;

    public ReplyListItem(String replyFrom, String replyTo, String replyDetail) {
        this.replyFrom = replyFrom;
        this.replyTo = replyTo;
        this.replyDetail = replyDetail;
    }

    public String getReplyFrom() {
        return replyFrom;
    }

    public void setReplyFrom(String replyFrom) {
        this.replyFrom = replyFrom;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getReplyDetail() {
        return replyDetail;
    }

    public void setReplyDetail(String replyDetail) {
        this.replyDetail = replyDetail;
    }
}
