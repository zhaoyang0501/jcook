package com.pzy.jcook.sys.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.sys.shiro.oauth2.OAuth2AuthenticationFilter;

@Controller
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()||subject.isRemembered()){
			return "index";
		} 
		  return "login";
	}
	/***
	 * 登陆的逻辑已经被shiro过滤器处理，这里只要读取shiro处理后得到的结果
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String dologin(HttpServletRequest req,Model model) {
		 String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");  
	        String error = null;  
	        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {  
	            error = "用户名/密码错误";  
	        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {  
	            error = "用户名/密码错误";  
	        } else if(exceptionClassName != null) {  
	            error = "其他错误：" + exceptionClassName;  
	        }  
	        model.addAttribute("tip", error);  
		return "login";
	}
	
	@RequestMapping(value = "/loginout", method = RequestMethod.GET)
	public String loginout(Model model) {
		SecurityUtils.getSubject().logout();
		return "login";
	}
	
	@RequestMapping(value = {"/","","index"}, method = RequestMethod.GET)
	public String  index(HttpSession session,Model model) {
		Subject subject = SecurityUtils.getSubject();
		 log.info("index----------isAuthenticated------------"+subject.isAuthenticated());
		 User user = (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
    	 log.info("index----------oauth2-----------inde get---"+user);
    	 log.info("index---------oauth2 in session-----------inde get---"+((User)session.getAttribute("currentUser")));
		return "index";
	}
}
