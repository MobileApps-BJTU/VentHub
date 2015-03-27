package com.vxpai.tucao.controllers;

import java.sql.Date;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {
	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public @ResponseBody String register(@RequestParam(value="email")String email,
											@RequestParam(value="username")String username,
											@RequestParam(value="password")String password){
		return null;
	}
	
	@RequestMapping(value="/getuserinfo", method=RequestMethod.POST)
	public @ResponseBody String getUserInfo(@RequestParam(value="email")String email){
		return null;
	}
	
	
	@RequestMapping(value="/edituserinfo", method=RequestMethod.POST)
	public @ResponseBody String editUserInfo(@RequestParam(value="email")String email,
												@RequestParam(value="currentpassword")String currentpassword,
												@RequestParam(value="newpassword")String newpassword,
												@RequestParam(value="dob")Date dob,
												@RequestParam(value="province")String province,
												@RequestParam(value="gender")String gender,
												@RequestParam(value="avatar")MultipartFile file){
		return null;
	}
	
}
