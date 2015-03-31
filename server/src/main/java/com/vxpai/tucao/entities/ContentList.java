package com.vxpai.tucao.entities;

import java.math.BigInteger;
import java.sql.Timestamp;

public class ContentList {
	private int cid;
	private String email;
	private String username;
	private String content;
	private Timestamp posttime;
	private String isannoy;
	private BigInteger approveNum;
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getPosttime() {
		return posttime;
	}
	public void setPosttime(Timestamp posttime) {
		this.posttime = posttime;
	}
	public String getIsannoy() {
		return isannoy;
	}
	public void setIsannoy(String isannoy) {
		this.isannoy = isannoy;
	}
	public BigInteger getApproveNum() {
		return approveNum;
	}
	public void setApproveNum(BigInteger approveNum) {
		this.approveNum = approveNum;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
