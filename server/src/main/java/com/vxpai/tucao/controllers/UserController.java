package com.vxpai.tucao.controllers;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.vxpai.tucao.entities.User;

@Controller
public class UserController {
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("finally")
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public @ResponseBody String register(@RequestParam(value="email")String email,
											@RequestParam(value="username")String username,
											@RequestParam(value="password")String password) throws UnsupportedEncodingException{
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(password);
		JSONObject json = new JSONObject();
		Session session = sessionFactory.openSession();
		try{
			Transaction tx = session.beginTransaction();
			session.save(user);
			tx.commit();
			json.put("status", "0");
		} catch (ConstraintViolationException e){
			json.put("status", "-2"); // 昵称已被注册 -1则为email已注册（见RegisterCheckInterceptor.java
		} finally {
			session.close();
			return json.toString();
		}
	}
	
	@RequestMapping(value="/getuserinfo", method=RequestMethod.POST)
	public @ResponseBody String getUserInfo(@RequestParam(value="fetchemail")String fetchemail){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where email=:email");
		query.setString("email", fetchemail);
		List<User> ls = query.list();
		if( ls.size()>0 ){
			User us = ls.get(0);
			us.setPassword("");
			JSONObject json = JSONObject.fromObject(us);
			return json.toString();
		} else {
			JSONObject json = new JSONObject();
			json.put("status", "-2");
			return json.toString();
		}
	}
	
	
	@SuppressWarnings("finally")
	@RequestMapping(value="/edituserinfo", method=RequestMethod.POST)
	public @ResponseBody String editUserInfo(@RequestParam(value="email")String email,
												@RequestParam(value="newpassword")String newpassword,
												@RequestParam(value="username")String username,
												@RequestParam(value="dob")Date dob,
												@RequestParam(value="province")String province,
												@RequestParam(value="gender")String gender){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where email=:email");
		query.setString("email",email);
		List<User> ls = query.list();
		User us = ls.get(0);
		if( !newpassword.equals("") ){
			us.setPassword(newpassword);
		}
		us.setDob(dob);
		us.setProvince(province);
		us.setGender(gender);
		us.setUsername(username);
		JSONObject json = new JSONObject();
		try{
			Transaction tx = session.beginTransaction();
			session.update(us);
			tx.commit();
			json.put("status", "0");
		} catch (ConstraintViolationException e){
			json.put("status", "-2"); // 昵称已被注册 -1则为email已注册（见RegisterCheckInterceptor.java
		} finally {
			session.close();
			return json.toString();
		}
	}
	
}
