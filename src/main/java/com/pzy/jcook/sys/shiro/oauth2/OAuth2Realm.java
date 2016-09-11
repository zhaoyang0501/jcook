package com.pzy.jcook.sys.shiro.oauth2;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.support.logging.Log;
import com.pzy.jcook.sys.entity.Role;
import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.sys.service.UserService;
import com.pzy.jcook.sys.shiro.MyRealm;

public class OAuth2Realm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory.getLogger(OAuth2Realm.class);
	
	private String clientId;
    private String clientSecret;
    private String accessTokenUrl;
    private String userInfoUrl;
    private String redirectUrl;

	@Autowired
	private UserService userService; 
	
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
    	log.info("supports_"+token);
        return token instanceof OAuth2Token;//表示此Realm只支持OAuth2Token类型
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	String currentUsername = (String) super.getAvailablePrincipal(principals);
		User user = userService.findByUsername(currentUsername);
		
		/**角色*/
		List<String> roleList = new ArrayList<String>();
		
		/**权限*/
		List<String> permissionList = new ArrayList<String>();
		if(!CollectionUtils.isNotEmpty(user.getRoles())){
			for (Role role : user.getRoles()) {
				roleList.add(role.getCode());
			}
		}
		
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRoles(roleList);
		simpleAuthorInfo.addStringPermissions(permissionList);
		return simpleAuthorInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("AuthenticationInfo___"+token);
        OAuth2Token oAuth2Token = (OAuth2Token) token;
        String code = oAuth2Token.getAuthCode();
        String username = "admin";
        User user=userService.findByUsername(username);
        SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
        SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
   	    log.info("reaml seting-----------set currentUser"+user);
        SimpleAuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(username, code, getName());
        return authenticationInfo;
      /*  SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, code, getName());
        SecurityUtils.getSubject().getSession().setAttribute("currentUser", userService.find(1233l));
        return authenticationInfo;*/
        /*User user=userService.findByUsername(username);
        if(user!=null){
        	 AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), getName());  
        	 SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
        	 log.info("oauth2-----------set currentUser"+user);
             return authcInfo;  
        }else{
        	  throw new UnknownAccountException();
        }*/
    }

    private String extractUsername(String code)  {
        try {
        	  log.info("extractUsername"+code);
              
        	OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
           
        	OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .tokenLocation(accessTokenUrl)
                    .setParameter("appid", clientId)
                    .setParameter("secret", clientSecret)
                    .setCode(code)
                    .setParameter("grant_type", "authorization_code")
                    .buildQueryMessage();
        	 log.info("accessTokenRequest---"+accessTokenRequest);
             
            OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);

            String accessToken = oAuthResponse.getAccessToken();
            String openid = oAuthResponse.getParam("openid");
            log.info("accessToken---"+accessToken);
            
            
            
            return "admin";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}