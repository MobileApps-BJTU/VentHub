package com.vxpai.tucao.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


// 在提交注册时检查email有无重复注册！
public class RegisterCheckInterceptor implements HandlerInterceptor{
	
	@Autowired
	private SessionFactory sessionFactory;

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		String email = request.getParameter("email");
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where email=:email");
		query.setString("email", email);
		List ls = query.list();
		JSONObject json = new JSONObject();
		if( ls.size()!=0 ){
			json.put("status", "-1"); // 邮箱已被注册！
			response.getWriter().println(json);
			return false;
		}
		return true;
	}

}
