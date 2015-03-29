package com.vxpai.tucao.controllers;

import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vxpai.tucao.entities.User;

@Controller
public class LoginController {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping(value="/checkuserinfo", method=RequestMethod.POST)
	public @ResponseBody String checkuserinfo(@RequestParam(value="email")String email, 
											  @RequestParam(value="password")String password){
		JSONObject json = new JSONObject();
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where email=:email and password=:password");
		query.setString("email", email);
		query.setString("password", password);
		List<User> ls = query.list();
		session.close();
		if(ls.size()>0){
			json.put("status", "0");
			return json.toString();
		}
		json.put("status","-1");
		return json.toString();
	}
	
}
