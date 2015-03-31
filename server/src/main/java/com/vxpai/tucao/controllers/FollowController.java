package com.vxpai.tucao.controllers;

import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.vxpai.tucao.entities.Follow;


@Controller
public class FollowController {
	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping(value="/follow",method=RequestMethod.POST)
	public @ResponseBody String follow(@RequestParam(value="email")String email,
												@RequestParam(value="followee")String followee){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where follower=:follower and followee=:folowee");
		query.setString("follower", email);
		query.setString("followee", followee);
		JSONObject json = new JSONObject();
		if( query.list().size()!=0 ){
			json.put("status", "-2"); // 已经存在该组关注关系
		} else {
			Follow follow = new Follow();
			follow.setFollower(email);
			follow.setFollowee(followee);
			Transaction tx = session.beginTransaction();
			session.save(follow);
			tx.commit();
			json.put("status", "0");
		}
		session.close();
		return json.toString();
	}
	
	@RequestMapping(value="/getfollowlist",method=RequestMethod.POST)
	public @ResponseBody String getfollowlist(@RequestParam(value="email")String email){
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery("select followee,username from vent_follow,vent_user where vent_follow.followee=vent_user.email and follower=:email");
		query.setString("email", email);
		List ls = query.list();
		String json = JSON.toJSONString(ls);
		return json;
	}
}
