package com.vxpai.tucao.controllers;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.vxpai.tucao.entities.Approve;
import com.vxpai.tucao.entities.Content;
import com.vxpai.tucao.entities.ContentList;


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
	
	@RequestMapping(value="/initventlist", method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public @ResponseBody String initventlist(@RequestParam(value="email")String email){
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery("select vent_content.cid,vent_content.email,content,username,posttime,isannoy,ifnull(approvenum,0) from vent_user,vent_content left join (select cid,count(*) as approvenum from vent_approve group by cid)t on t.cid=vent_content.cid where vent_content.email=vent_user.email and (vent_content.email=:email or vent_content.email in (select followee from vent_follow where follower=:email) ) order by posttime desc;");
		query.setString("email", email);
		List ls = query.list();
		List res = new ArrayList<ContentList>();
		//Object obj = ls.get(0);
		for(int i=0;i<ls.size();i++){
			Object[] obj = (Object[]) ls.get(i);
			ContentList cl = new ContentList();
			cl.setCid((Integer) obj[0]);
			cl.setEmail((String) obj[1]);
			cl.setContent((String)obj[2]);
			cl.setUsername((String)obj[3]);
			cl.setPosttime((Timestamp)obj[4]);
			cl.setIsannoy((String)obj[5]);
			cl.setApproveNum((BigInteger)obj[6]);
			res.add(cl);
		}
		return JSON.toJSONString(res);
	}
	
	@RequestMapping(value="/getventlist",method=RequestMethod.POST)
	public @ResponseBody String getventlist(@RequestParam(value="email")String email,
												@RequestParam(value="from")String from){
		return null;
	}
	
	@RequestMapping(value="/approve", method=RequestMethod.POST)
	public @ResponseBody String approve(@RequestParam(value="email")String email,
										@RequestParam(value="cid")int cid){
		Approve approve = new Approve();
		approve.setEmail(email);
		approve.setCid(cid);
		Session session = sessionFactory.openSession();
		JSONObject json = new JSONObject();
		Query query = session.createQuery("from Approve where email=:email and cid=:cid");
		query.setString("email", email);
		query.setString("cid", Integer.toString(cid));
		List ls = query.list();
		if( ls.size()!=0 ){
			json.put("status", "-2");
		} else {
			approve.setEmail(email);
			approve.setCid(cid);
			Transaction tx = session.beginTransaction();
			session.save(approve);
			tx.commit();
		}
		session.close();
		return json.toString();
	}
	
	@RequestMapping(value="/gettop100",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public @ResponseBody String gettop100(){
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery("select vent_content.cid,vent_content.email,content,username,posttime,isannoy,ifnull(approvenum,0) from vent_user,vent_content left join (select cid,count(*) as approvenum from vent_approve group by cid)t on t.cid=vent_content.cid where vent_content.email=vent_user.email order by approvenum desc limit 100;");
		List ls = query.list();
		List res = new ArrayList<ContentList>();
		//Object obj = ls.get(0);
		for(int i=0;i<ls.size();i++){
			Object[] obj = (Object[]) ls.get(i);
			ContentList cl = new ContentList();
			cl.setCid((Integer) obj[0]);
			cl.setEmail((String) obj[1]);
			cl.setContent((String)obj[2]);
			cl.setUsername((String)obj[3]);
			cl.setPosttime((Timestamp)obj[4]);
			cl.setIsannoy((String)obj[5]);
			cl.setApproveNum((BigInteger)obj[6]);
			res.add(cl);
		}
		return JSON.toJSONString(res);
	}
	
}
