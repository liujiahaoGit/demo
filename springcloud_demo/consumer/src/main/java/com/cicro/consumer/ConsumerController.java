package com.cicro.consumer;

import com.cicro.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ConsumerController {

    @GetMapping("/test1")
    public String test1() {

        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://127.0.0.1:1113/provider");
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String s = bf.readLine();
                bf.close();
                return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "error";
    }

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestTemplate restTemplateOne;

    @GetMapping("/test2")
    public String test2() {
        List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        ServiceInstance instance = provider.get(0);
        StringBuilder sb = new StringBuilder();

        sb.append(instance.getUri())
            .append("/provider");
        String result = restTemplateOne.getForObject(sb.toString(), String.class);

        return result;

    }

    @GetMapping("/test4")
    public String test4() {

        String result = restTemplate.getForObject("http://provider/provider", String.class);

        return result;

    }

    int count = 0;

    @GetMapping("/test3")
    public String test3() {
        List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        ServiceInstance instance = provider.get((count++) % provider.size());
        StringBuilder sb = new StringBuilder();
        sb.append(instance.getUri())
            .append("/provider");
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sb.toString());
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String s = bf.readLine();
                bf.close();
                return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "error";

    }

    @GetMapping("/test5")
    public void test5() {

        String result = restTemplate.getForObject("http://provider/provider1?name={1}", String.class, "zs");
        System.out.println("result = " + result);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zs");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://provider/provider1?name={name}",
            String.class, map);
        String body = responseEntity.getBody();
        System.out.println("body = " + body);
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println("statusCode = " + statusCode);
        int codeValue = responseEntity.getStatusCodeValue();
        System.out.println("codeValue = " + codeValue);
        HttpHeaders headers = responseEntity.getHeaders();
        System.out.println("headers:----------------------");
        headers.forEach((x, y) -> {
            System.out.println(x + ":" + y);
        });

    }

    @GetMapping("/test6")
    public void test6() throws UnsupportedEncodingException {

        //第一种
        ResponseEntity<String> entity = restTemplate.getForEntity("http://provider/provider1?name={1}", String.class,
            "李四");
        System.out.println("body1:" + entity.getBody());

        //第二种
        URI uri = URI.create("http://provider/provider1?name=" + URLEncoder.encode("李四", "UTF-8"));
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);
        System.out.println("body2:" + forEntity.getBody());

        //第三种
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zs");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://provider/provider1?name={name}",
            String.class, map);
        System.out.println("body3:" + responseEntity.getBody());
    }

    @GetMapping("/test7")
    public void test7() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("id", 1);
        map.add("username", "zhangsan");
        map.add("password", "1230");
        User user = restTemplate.postForObject("http://provider/user", map, User.class);
        System.out.println("user = " + user);

        user.setId(2);
        User user1 = restTemplate.postForObject("http://provider/user1", user, User.class);
        System.out.println("user1 = " + user1);
    }

    @GetMapping("/test8")
    public String test8() {
        User user = new User();
        user.setId(3);
        user.setUsername("lisi");
        user.setPassword("123");
        //调用该方法返回的是一个 Uri，这个 Uri 就是重定向的地址（里边也包含了重定向的参数），拿到 Uri 之后，就可以直接发送新的请求了。
        URI uri = restTemplate.postForLocation("http://provider/register", user);
        String s = restTemplate.getForObject(uri, String.class);
        System.out.println("uri = " + uri);
        System.out.println("s = " + s);
        return s;
    }

    @GetMapping("/test9")
    public void test9() {
        User user = new User();
        user.setId(3);
        user.setUsername("lisi");
        user.setPassword("123");
        restTemplate.put("http://provider/user4", user);


        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("id", 1);
        map.add("username", "zhangsan");
        map.add("password", "1230");
        restTemplate.put("http://provider/user3",map);
    }

    @GetMapping("/test10")
    public void test10() {
       // restTemplate.delete("http://provider/delete/{1}",1);
        restTemplate.delete("http://provider/delete?id={1}",2);
    }

}
