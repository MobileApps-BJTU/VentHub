package com.vxpai.tucao.controllers;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class FollowController {
	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping(value="/follow",method=RequestMethod.POST)
	public @ResponseBody String follow(@RequestParam(value="email")String email,
												@RequestParam(value="followee")String followee){
		return null;		
	}
	
	@RequestMapping(value="/getfollowlist",method=RequestMethod.POST)
	public @ResponseBody String getfollowlist(@RequestParam(value="email")String email,
												@RequestParam(value="from")String from){
		return null;		
	}
}
