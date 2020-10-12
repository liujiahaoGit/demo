package com.cicro.redis.jedis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.dynamic.RedisCommandFactory;

import java.util.List;

public class ThrottleTest {
    public static void main(String[] args) {
        RedisClient client = RedisClient.create("redis://123@192.168.13.128");
        StatefulRedisConnection<String, String> connect = client.connect();
        RedisCommandFactory factory=new RedisCommandFactory(connect);
        RedisCommandInterface commands = factory.getCommands(RedisCommandInterface.class);
        List<Object> test = commands.throttle("test", 10L, 3L, 10L, 1L);
        System.out.println("test = " + test);
    }
}
