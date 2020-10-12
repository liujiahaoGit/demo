package com.cicro.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class SentinelTest {
    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        //连接池最大连接数
        config.setMaxTotal(1000);
        //最大空闲数
        config.setMaxIdle(300);
        //最大等待时间
        config.setMaxWaitMillis(30000);
        //在空闲时检查有效性
        config.setTestOnBorrow(true);
        Set<String> set = new HashSet<>();
        set.add("192.168.120.128:26379");
        String master = "mymaster";
        JedisSentinelPool sentinelPool = new JedisSentinelPool(master, set, config, "123");
        Jedis jedis=null;
        while (true) {
            try {
                jedis = sentinelPool.getResource();
                String name = jedis.get("name");
                System.out.println("name = " + name);

                Thread.sleep(5000);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }
}
