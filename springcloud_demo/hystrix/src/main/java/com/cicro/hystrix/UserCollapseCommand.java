package com.cicro.hystrix;

import com.cicro.common.User;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * @className: UserCollapseCommand
 * @description TODO
 * @since JDK1.8
 * @author ljh
 * @createdAt  2020/9/23 0023
 * @version 1.0.0
 **/
public class UserCollapseCommand extends HystrixCollapser<List<User>, User, Integer> {

    private UserService userService;

    private Integer id;

    public UserCollapseCommand(UserService userService, Integer id) {
        super(HystrixCollapser.Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("userCollapseCommand"))
            .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
                .withTimerDelayInMilliseconds(200))); //定义超时时间,当超过此时间时的请求到来时,请求会进入到下一批请求中合并发出
        this.userService = userService;
        this.id = id;
    }

    /**
     * 请求参数
     *
     * @return
     */
    @Override
    public Integer getRequestArgument() {
        return id;
    }

    /**
     * 请求合并方法
     *
     * @param collection
     * @return
     */
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Integer>> collection) {
        List<Integer> ids = new ArrayList<>();
        for (CollapsedRequest<User, Integer> request : collection) {
            ids.add(request.getArgument());
        }

        return new UserBatchCommand(ids, userService);
    }

    /**
     * 请求结果分发
     *
     * @param users
     * @param collection
     */
    @Override
    protected void mapResponseToRequests(List<User> users, Collection<CollapsedRequest<User, Integer>> collection) {
        int count = 0;
        for (CollapsedRequest<User, Integer> request : collection) {
            request.setResponse(users.get(count++));
        }
    }
}
