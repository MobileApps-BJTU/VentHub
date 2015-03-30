package com.vxpai.tucao.controllers;

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

import com.vxpai.tucao.entities.Content;


@Controller
public class ContentController {
	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping(value="/postcontent", method=RequestMethod.POST)
	public @ResponseBody String postContent(@RequestParam(value="email")String email,
											@RequestParam(value="content")String content,
											@RequestParam(value="isannoy")String isannoy){
		Content con = new Content();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		con.setEmail(email);
		con.setContent(content);
		con.setIsAnnoy(isannoy);
		session.save(con);
		tx.commit();
		session.close();
		JSONObject json = new JSONObject();
		json.put("status", "0");
		return json.toString();
	}
	
	@RequestMapping(value="/getcontentdetail",method=RequestMethod.POST)
	public @ResponseBody String getContentDetail(@RequestParam(value="cid")int cid){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Content where cid=:cid");
		query.setString("cid",Integer.toString(cid));
		Content con = (Content) query.list().get(0);
		if(con.getIsAnnoy().equals("YES")){
			con.setEmail("");
		}
		JSONObject json = JSONObject.fromObject(con);
		session.close();
		return json.toString();		
	}
	
	@RequestMapping(value="/initventlist",method=RequestMethod.POST)
	public @ResponseBody String initventlist(@RequestParam(value="email")String email,
												@RequestParam(value="lasttime")String lasttime){
		return null;
	}
	
	@RequestMapping(value="/getventlist",method=RequestMethod.POST)
	public @ResponseBody String getventlist(@RequestParam(value="email")String email,
												@RequestParam(value="from")String from){
		return null;
	}
	
}
