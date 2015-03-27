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
@Table(name="vent_approve")

public class Approve {
	
	@Id
	@Column(name="aid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int aid;
	
	@Column(name="cid")
	private int cid;
	
	@Column(name="email")
	private String email;
	
	@ManyToOne(targetEntity=Content.class,cascade=CascadeType.ALL,optional=true,fetch=FetchType.LAZY)
	@JoinColumn(name="cid",insertable=false,updatable=false)
	private Content vent;
	
	@ManyToOne(targetEntity=User.class,cascade=CascadeType.ALL,optional=true,fetch=FetchType.LAZY)
	@JoinColumn(name="email",insertable=false,updatable=false)
	private User approver;	
	
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
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public Content getVent() {
		return vent;
	}
	public void setVent(Content vent) {
		this.vent = vent;
	}
	public User getApprover() {
		return approver;
	}
	public void setApprover(User approver) {
		this.approver = approver;
	}
}
