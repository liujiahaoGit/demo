package com.cicro.oauth2.oauth2_userclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class HelloController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TokenTask tokenTask;

    /**
     * 授权码模式
     *
     * @param
     * code：表示上一步获得的授权码，必选项。
     * redirect_uri：表示重定向URI，必选项，且必须与获取授权码中的该参数值保持一致。
     * client_id：表示客户端ID，必选项。
     * grant_type：表示使用的授权模式，必选项，此处的值固定为"authorization_code"。
     * @param model
     * @return
     */
    @GetMapping("/index.html")
    public String hellp(String code, Model model) {
       /* if (code != null) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("code", code);
            map.add("client_id", "javaboy");
            map.add("client_secret", "123");
            map.add("redirect_uri", "http://localhost:8082/index.html");  //这里的重定向地址要和授权服务器中的redirect_uri一致
            map.add("grant_type", "authorization_code");
            Map<String, String> resp = restTemplate.postForObject("http://localhost:8080/oauth/token", map, Map.class);
            String token = resp.get("access_token");
            System.out.println("resp = " + resp);

            //access_token 通过请求头传递
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8081/admin/hello",
                HttpMethod.GET, httpEntity, String.class);
            model.addAttribute("msg", entity.getBody());
        }*/
        model.addAttribute("msg", tokenTask.getData(code));
        return "index";
    }

    /**
     * 密码模式
     *
     * @param username
     *        client_id: 表示客户端ID，必选项。
     *        grant_type：表示授权类型，此处的值固定为"password"，必选项。
     *        username：表示用户名，必选项。
     *        password：表示用户的密码，必选项。
     *        scope：表示权限范围，可选项。
     * @param password
     * @param model
     * @return
     */
    @PostMapping("/login")
    public String login(String username, String password, Model model) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("client_secret", "123");
        map.add("client_id", "javaboy");
        map.add("grant_type", "password");
        Map<String, String> resp = restTemplate.postForObject("http://localhost:8080/oauth/token", map, Map.class);
        System.out.println("resp = " + resp);

        String access_token = resp.get("access_token");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + access_token);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8081/admin/hello", HttpMethod.GET,
            httpEntity, String.class);
        model.addAttribute("msg", entity.getBody());
        return "login.html";

    }

    @RequestMapping("/login.html")
    public String loginPage() {
        return "login";
    }

}
