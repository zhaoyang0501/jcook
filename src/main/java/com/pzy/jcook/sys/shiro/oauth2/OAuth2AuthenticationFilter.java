package com.pzy.jcook.sys.shiro.oauth2;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.sys.service.UserService;
import com.pzy.jcook.sys.shiro.MyRealm;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-18
 * <p>Version: 1.0
 */
public class OAuth2AuthenticationFilter extends AuthenticatingFilter {
	private static final Logger log = LoggerFactory.getLogger(OAuth2AuthenticationFilter.class);
	
    //oauth2 authc code参数名
    private String authcCodeParam = "code";
    //客户端id
    private String clientId;
    //服务器端登录成功/失败后重定向到的客户端地址
    private String redirectUrl;
    //oauth2服务器响应类型
    private String responseType = "code";

    private String failureUrl;
    
    @Autowired
    private UserService userservice;
    
    public void setAuthcCodeParam(String authcCodeParam) {
        this.authcCodeParam = authcCodeParam;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String code = httpRequest.getParameter(authcCodeParam);
        return new OAuth2Token(code);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

    	log.info("onAccessDenied____");
        String error = request.getParameter("error");
        String errorDescription = request.getParameter("error_description");
        if(!StringUtils.isEmpty(error)) {//如果服务端返回了错误
            WebUtils.issueRedirect(request, response, failureUrl + "?error=" + error + "error_description=" + errorDescription);
            return false;
        }

        Subject subject = getSubject(request, response);
        
        if(!subject.isAuthenticated()) {
            if(!StringUtils.isEmpty(request.getParameter(authcCodeParam))) {
            	 log.info("executeLogin____________");
                 return executeLogin(request, response);
            }
            
        }
        return true;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
    	
		log.info("index----------isAuthenticated------------"+SecurityUtils.getSubject().isAuthenticated());
    	User user = (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
    	 log.info("onLoginSuccess----------oauth2-----------inde get---"+user);
    	 HttpServletResponse p =(HttpServletResponse)response;
    	 HttpServletRequest r =(HttpServletRequest)request;
    	// p.sendRedirect(r.getContextPath()+"/workflow/tasktodo");
    	  issueSuccessRedirect(request, response);
         return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {
    	 log.info("executeLogin____________");
    	Subject subject = getSubject(request, response);
        if (subject.isAuthenticated() || subject.isRemembered()) {
            try {
                issueSuccessRedirect(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                WebUtils.issueRedirect(request, response, failureUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}