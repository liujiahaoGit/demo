package com.cicro.hystrix;

import com.cicro.common.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class HystrixController {

    @Autowired
    private HystrixService hystrixService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hystrix")
    public String test() {

        return hystrixService.hystrix();
    }

    @GetMapping("/hystrix1")
    public String test1() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        HelloCommand helloCommand =
            new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("test")),
                restTemplate,"测试");
        String execute = helloCommand.execute();

        HelloCommand helloCommand1 =
            new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("test")),
                restTemplate,"测试");
        Future<String> queue = helloCommand1.queue(); //异步调用
        String s = null;
        try {
            s = queue.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        context.close();
        return s;
    }

    @GetMapping("/hystrix2")
    public String test2() throws ExecutionException, InterruptedException {
        Future<String> stringFuture = hystrixService.hystrix1();
        return stringFuture.get();
    }

    @GetMapping("/hystrix3")
    public String test3() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        //第一请求完，数据已经缓存下来了
        hystrixService.hystrix2("测试");
        //删除数据，同时缓存中的数据也会被删除
        hystrixService.deleteHystrix("测试");
        //第二次请求时，虽然参数还是 测试，但是缓存数据已经没了，所以这一次，provider 还是会收到请求
        hystrixService.hystrix2("测试");
        context.close();
        return null;

    }

    @Autowired
    UserService userService;

    @GetMapping("/hystrix4")
    public void test4() throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        UserCollapseCommand userCollapseCommand4=new UserCollapseCommand(userService,4);
        UserCollapseCommand userCollapseCommand1=new UserCollapseCommand(userService,1);
        UserCollapseCommand userCollapseCommand2=new UserCollapseCommand(userService,2);
        UserCollapseCommand userCollapseCommand3=new UserCollapseCommand(userService,3);

        Future<User> queue1 = userCollapseCommand1.queue();
        Future<User> queue2 = userCollapseCommand2.queue();
        Future<User> queue3 = userCollapseCommand3.queue();
        Future<User> queue4 = userCollapseCommand4.queue();
        User user1 = queue1.get();
        User user2 = queue2.get();
        User user3 = queue3.get();


        System.out.println("user1 = " + user1);
        System.out.println("user2 = " + user2);
        System.out.println("user3 = " + user3);


        User user4 = queue4.get();
        System.out.println("user4 = " + user4);
        context.close();
    }


    @GetMapping("/hystrix5")
    public void test5() throws ExecutionException, InterruptedException {
        Future<User> future1 = userService.getUser(1);
        Future<User> future2 = userService.getUser(2);
        Future<User> future3 = userService.getUser(3);
        Future<User> future4 = userService.getUser(4);
        User user1 = future1.get();
        User user2 = future2.get();
        User user3 = future3.get();
        User user4 = future4.get();
        System.out.println("user1 = " + user1);
        System.out.println("user2 = " + user2);
        System.out.println("user3 = " + user3);
        System.out.println("user4 = " + user4);

    }
}
