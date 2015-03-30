package com.vxpai.tucao.controllers;

import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vxpai.tucao.entities.Comment;


@Controller
public class CommentController {
	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping(value="/addcomment",method=RequestMethod.POST)
	public @ResponseBody String addcomment(@RequestParam(value="email")String email,
												@RequestParam(value="cid")String cid,
												@RequestParam(value="content")String content){
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Comment com = new Comment();
		com.setCid(Integer.parseInt(cid));
		com.setEmail(email);
		com.setContent(content);
		session.save(com);
		tx.commit();
		session.close();
		JSONObject json = new JSONObject();
		json.put("status", "0");
		return json.toString();
	}
	
	@RequestMapping(value="/getcommentlist",method=RequestMethod.POST)
	public @ResponseBody String getcommentlist(@RequestParam(value="cid")String cid,
												@RequestParam(value="from")String from){
		return null;
	}
}
