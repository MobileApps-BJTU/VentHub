package com.vxpai.tucao.entities;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="vent_comment")

public class Comment {
	
	@Id
	@Column(name="comid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int comid;
	
	@Column(name="email")
	private String email;
	
	@Column(name="cid")
	private int cid;
	
	@Column(name="content")
	private String content;
	
	@Column(name="posttime")
	private Timestamp posttime;
	
	@ManyToOne(targetEntity=User.class,cascade=CascadeType.ALL,optional=true,fetch=FetchType.LAZY)
	@JoinColumn(name="email",insertable=false,updatable=false)
	private User author;
	
	@ManyToOne(targetEntity=Content.class,cascade=CascadeType.ALL,optional=true,fetch=FetchType.LAZY)
	@JoinColumn(name="cid",insertable=false,updatable=false)
	private Content vent;
	
	
	public int getComid() {
		return comid;
	}
	public void setComid(int comid) {
		this.comid = comid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
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
	public Content getVent() {
		return vent;
	}
	public void setVent(Content vent) {
		this.vent = vent;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	
	

}
