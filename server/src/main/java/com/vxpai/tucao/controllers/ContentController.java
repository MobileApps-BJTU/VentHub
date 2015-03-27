package com.vxpai.tucao.controllers;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ContentController {
	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping(value="/postcontent", method=RequestMethod.POST)
	public @ResponseBody String postContent(@RequestParam(value="email")String email,
											@RequestParam(value="content")String content,
											@RequestParam(value="isannoy")String isannoy){
		
		return null;
	}
	
	@RequestMapping(value="/getcontentdetail",method=RequestMethod.POST)
	public @ResponseBody String getContentDetail(@RequestParam(value="email")String email,
												@RequestParam(value="cid")int cid){
		return null;		
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
