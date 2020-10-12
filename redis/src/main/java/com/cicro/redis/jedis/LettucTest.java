package com.cicro.redis.jedis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class LettucTest {
    public static void main(String[] args) {
        RedisClient client = RedisClient.create("redis://123@192.168.32.128");
        StatefulRedisConnection<String, String> connect = client.connect();
        RedisCommands<String, String> commands = connect.sync();

        commands.set("name","123");
        System.out.println("commands.get(\"name\") = " + commands.get("name"));
    }
}
