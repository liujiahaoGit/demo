package com.cicro.hystrix;

import com.cicro.common.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;


public class UserBatchCommand  extends HystrixCommand<List<User>> {


    List<Integer> ids;

    UserService userService;

    public UserBatchCommand( List<Integer> ids, UserService userService) {
        super(HystrixCommandGroupKey.Factory.asKey("batchCommand"));
        this.ids = ids;
        this.userService = userService;
    }

    @Override
    protected List<User> run() throws Exception {
        return userService.getUsers(ids);
    }
}
