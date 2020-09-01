package com.cicro.oauth2.oauth2_server.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;


/*
 * @className: CustomAdditionalInformation
 * @description enhance 方法中的 OAuth2AccessToken 参数就是已经生成的 access_token 信息，
 *                      我们可以从 OAuth2AccessToken 中取出已经生成的额外信息，然后在此基础上追加自己的信息。
 * @since JDK1.8
 * @author ljh
 * @createdAt  2020/8/26 0026 
 * @version 1.0.0
 **/
@Component
public class CustomAdditionalInformation implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> info = new LinkedHashMap(accessToken.getAdditionalInformation());
        info.put("author", "江南一点雨");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
