package com.cicro.oauth2_github.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthorizationController {


    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/authorization_code")
    public String authorization_code(String code){
        Map<String, String> map = new HashMap<>();
        map.put("client_id", "2009b888c029750fb66c");
        map.put("client_secret", "373990b0c04ba5826ebfe457151c9233e68728a3");
        map.put("state", "demo");
        map.put("code", code);
        map.put("redirect_uri", "http://localhost:8080/authorization_code"); //这里为在github中注册的授权成功的回调地址
        Map<String,String> resp = restTemplate.postForObject("https://github.com/login/oauth/access_token", map, Map.class);

        System.out.println(resp);

        //将token放入请求头中 请求用户信息接口
        HttpHeaders httpheaders = new HttpHeaders();
        httpheaders.add("Authorization", "token " + resp.get("access_token"));
        HttpEntity<?> httpEntity = new HttpEntity<>(httpheaders);
        ResponseEntity<Map> exchange = restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, httpEntity, Map.class);

        System.out.println("exchange.getBody() = " + exchange.getBody());
        return "index";
    }

}
