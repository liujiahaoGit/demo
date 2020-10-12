package com.cicro.redis.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPoolTest {

    public static void main(String[] args) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        //连接池最大连接数
        config.setMaxTotal(1000);
        //最大空闲数
        config.setMaxIdle(300);
        //最大等待时间
        config.setMaxWaitMillis(30000);
        //在空闲时检查有效性
        config.setTestOnBorrow(true);
        JedisPool pool = new JedisPool(config, "192.168.32.128", 6379, 10000, "123");

        //Jdk1.7之后提供的语法糖 try...with..resources 不用在catch finally里手动归还(关闭)连接了
        try (Jedis jedis = pool.getResource()) {
            System.out.println("jedis.ping() = " + jedis.ping());
        }
    }
}
