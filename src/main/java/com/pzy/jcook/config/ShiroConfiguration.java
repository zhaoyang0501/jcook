package com.pzy.jcook.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.DispatcherServlet;

import com.pzy.jcook.sys.shiro.CustomCredentialsMatcher;
import com.pzy.jcook.sys.shiro.MyRealm;
public class ShiroConfiguration {
	
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
	    ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
	    shiroFilter.setLoginUrl("/login");
	    shiroFilter.setSuccessUrl("/index");
	    shiroFilter.setUnauthorizedUrl("/forbidden");
	   
	    Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();
	    filterChainDefinitionMapping.put("/", "authc");
	    filterChainDefinitionMapping.put("/login", "authc");
	    filterChainDefinitionMapping.put("/loginout", "logout");
	    filterChainDefinitionMapping.put("/index", "authc");
	    shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
	   
	    shiroFilter.setSecurityManager(securityManager());
	    Map<String, Filter> filters = new HashMap<String, Filter>();
	    filters.put("anon", new AnonymousFilter());
	    filters.put("authc", new FormAuthenticationFilter());
	    filters.put("logout", new LogoutFilter());
	    filters.put("roles", new RolesAuthorizationFilter());
	    filters.put("user", new UserFilter());
	    shiroFilter.setFilters(filters);
	    System.out.println(shiroFilter.getFilters().size());
	    return shiroFilter;
	}

	@Bean(name = "securityManager")
	public SecurityManager securityManager() {
	    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
	    securityManager.setRealm(realm());
	    securityManager.setRememberMeManager(rememberMeManager());
	    securityManager.setCacheManager(shiroCacheManager());
	    return securityManager;
	}
	
	private RememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		cookieRememberMeManager.setCookie(rememberMeCookie());
		return cookieRememberMeManager;
	}

	private Cookie rememberMeCookie() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * TODO 改成enchche
	 * @return
	 */
	@Bean(name = "shiroCacheManager")
	public CacheManager shiroCacheManager() {
	    return new MemoryConstrainedCacheManager();
	}

	@Bean(name = "realm")
	@DependsOn("lifecycleBeanPostProcessor")
	public MyRealm realm() {
		MyRealm myRealm = new MyRealm();
		myRealm.setCredentialsMatcher(new CustomCredentialsMatcher() );
		myRealm.setCachingEnabled(true);
		myRealm.setAuthenticationCachingEnabled(false);
		myRealm.setAuthorizationCachingEnabled(false);
		myRealm.setAuthorizationCacheName("authorizationCache");
		return myRealm;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
	    return new LifecycleBeanPostProcessor();
	}
	/*@Bean
	  public ServletRegistrationBean servletRegistrationBean() {
		 DispatcherServlet dispatcherServlet =  new DispatcherServlet();
		 dispatcherServlet.setContextConfigLocation("classpath:spring/applicationContext-activitrest.xml");
	    return new ServletRegistrationBean(new DispatcherServlet(), "/service/*");
	  }*/
	
}
