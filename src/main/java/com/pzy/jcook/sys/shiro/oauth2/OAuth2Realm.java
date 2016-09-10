package com.pzy.jcook.sys.shiro.oauth2;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pzy.jcook.sys.shiro.MyRealm;

public class OAuth2Realm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory.getLogger(OAuth2Realm.class);
	
	private String clientId;
    private String clientSecret;
    private String accessTokenUrl;
    private String userInfoUrl;
    private String redirectUrl;

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
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        log.info("doGetAuthorizationInfo"+authorizationInfo);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("AuthenticationInfo"+token);
        
    	OAuth2Token oAuth2Token = (OAuth2Token) token;
        String code = oAuth2Token.getAuthCode();
        String username = extractUsername(code);

        SimpleAuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(username, code, getName());
        return authenticationInfo;
    }

    private String extractUsername(String code)  {
        try {
        	  log.info("extractUsername"+code);
              
        	OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
           
        	OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .tokenLocation(accessTokenUrl)
                    .setParameter("appid", clientId)
                    .setClientSecret(clientSecret)
                    .setCode(code)
                    .setRedirectURI(redirectUrl)
                    .buildQueryMessage();
        	 log.info("accessTokenRequest---"+accessTokenRequest);
             
            OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);

            String accessToken = oAuthResponse.getAccessToken();
            log.info("accessToken---"+accessToken);
            
            Long expiresIn = oAuthResponse.getExpiresIn();

            OAuthClientRequest userInfoRequest = new OAuthBearerClientRequest(userInfoUrl)
                    .setAccessToken(accessToken).buildQueryMessage();

            OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            String username = resourceResponse.getBody();
            log.info("username---"+accessToken);
            
            return username;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}