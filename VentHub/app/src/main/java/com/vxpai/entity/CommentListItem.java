package com.vxpai.entity;

import java.util.List;

/**
 * Created by 俊成 on 2015/3/30.
 */
public class CommentListItem {

    private String headImgPath;
    private String nickName;
    private String commentTime;
    private String commentDetail;
    private List<ReplyListItem> replyList;

    public CommentListItem(String headImgPath, String nickName, String commentDetail, List<ReplyListItem> replyList, String commentTime) {
        this.headImgPath = headImgPath;
        this.nickName = nickName;
        this.commentDetail = commentDetail;
        this.replyList = replyList;
        this.commentTime = commentTime;
    }

    public String getHeadImgPath() {
        return headImgPath;
    }

    public void setHeadImgPath(String headImgPath) {
        this.headImgPath = headImgPath;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
