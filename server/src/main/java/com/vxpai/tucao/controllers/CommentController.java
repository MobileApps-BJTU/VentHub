package com.vxpai.tucao.controllers;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CommentController {
	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping(value="/addcomment",method=RequestMethod.POST)
	public @ResponseBody String addcomment(@RequestParam(value="email")String email,
												@RequestParam(value="cid")String cid,
												@RequestParam(value="content")String content){
		return null;
	}
	
	@RequestMapping(value="/getcommentlist",method=RequestMethod.POST)
	public @ResponseBody String getcommentlist(@RequestParam(value="cid")String cid,
												@RequestParam(value="from")String from){
		return null;
	}
}
