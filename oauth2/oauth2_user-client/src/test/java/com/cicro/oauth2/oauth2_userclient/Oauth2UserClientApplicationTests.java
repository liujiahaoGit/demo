package com.cicro.oauth2.oauth2_userclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest
class Oauth2UserClientApplicationTests {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 客户端模式测试(因为客户端模式只有后台 没有前台页面,因此在这里测试)
     * client_id: 表示客户端ID，必选项。
     * grant_type：表示授权类型，此处的值固定为"client_credentials"，必选项.
     * scope：表示权限范围，可选项。
     */
    @Test
    void contextLoads() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "javaboy");
        map.add("client_secret", "123");
        map.add("grant_type", "client_credentials");
        Map<String, String> resp = restTemplate.postForObject("http://localhost:8080/oauth/token", map, Map.class);
        String access_token = resp.get("access_token");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + access_token);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8081/hello", HttpMethod.GET,
            httpEntity, String.class);
        System.out.println(entity.getBody());
    }


}
