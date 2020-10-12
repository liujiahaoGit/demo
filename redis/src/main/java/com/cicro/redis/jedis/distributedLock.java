package com.cicro.redis.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Arrays;
import java.util.UUID;

public class distributedLock {
    public static void main(String[] args) throws InterruptedException {
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
            for (int i = 0; i < 10; i++) {
                String s = UUID.randomUUID().toString();
                String set = jedis.set("name", "zs", new SetParams().nx().ex(10)); //给锁添加一个过期时间，防止应用在运行过程中抛出异常导致锁无法及时得到释放
                if (set != null && "OK".equals(set)) {

                    //拿到锁后执行相关业务逻辑
                    String name = jedis.get("name");
                    System.out.println("name = " + name);

                    //调用服务端提前写好的lua脚本 释放锁资源
                    jedis.evalsha("b8059ba43af6ffe8bed3db65bac35d452f8115d8", Arrays.asList("name"), Arrays.asList(s));

                } else {
                    System.out.println("没有拿到锁"); //做出相应的暂缓操作
                }
            }
        }
    }
}
