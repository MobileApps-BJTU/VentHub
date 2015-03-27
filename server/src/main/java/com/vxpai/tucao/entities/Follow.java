package com.vxpai.tucao.entities;

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
@Table(name="vent_follow")

public class Follow {
	@Id
	@Column(name="fid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int fid;
	
	@Column(name="follower")
	private String follower;
	
	@Column(name="followee")
	private String followee;
	
	@ManyToOne(targetEntity=User.class,cascade=CascadeType.ALL,optional=true,fetch=FetchType.LAZY)
	@JoinColumn(name="follower",insertable=false,updatable=false)
	private User userFollower;	
	
	@ManyToOne(targetEntity=User.class,cascade=CascadeType.ALL,optional=true,fetch=FetchType.LAZY)
	@JoinColumn(name="followee",insertable=false,updatable=false)
	private User userFollowee;	
	
	public String getFollower() {
		return follower;
	}
	public void setFollower(String follower) {
		this.follower = follower;
	}
	public String getFollowee() {
		return followee;
	}
	public void setFollowee(String followee) {
		this.followee = followee;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public User getUserFollower() {
		return userFollower;
	}
	public void setUserFollower(User userFollower) {
		this.userFollower = userFollower;
	}
	public User getUserFollowee() {
		return userFollowee;
	}
	public void setUserFollowee(User userFollowee) {
		this.userFollowee = userFollowee;
	}
}
