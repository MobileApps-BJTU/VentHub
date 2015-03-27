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

import com.vxpai.tucao.entities.User;

public class CheckUserPasswordInterceptor implements HandlerInterceptor {

	@Autowired
	private SessionFactory sessionFactory;
	
	public String[] allowUrls;
	
	public void setAllowUrls(String[] allowUrls){
		this.allowUrls = allowUrls;
	}
	
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		System.out.println("Interpretor executed!");
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		if( null!=allowUrls && allowUrls.length>=1 ){
			for(String url:allowUrls){
				if(requestUrl.contains(url)){
					return true;
				}
			}
		}
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		JSONObject json = new JSONObject();
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where email=:email and password=:password");
		query.setString("email", email);
		query.setString("password", password);
		List<User> ls = query.list();
		if(ls.size()==0){
			json.put("status", "-1");
			response.getWriter().println(json.toString());
			return false;
		}
		return true;
	}

}
