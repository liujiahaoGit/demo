package com.cicro.redis.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class QueueTest {
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
        JedisPool pool = new JedisPool(config, "192.168.228.128", 6379, 10000, "123");

        //Jdk1.7之后提供的语法糖 try...with..resources 不用在catch finally里手动归还(关闭)连接了
        try (Jedis jedis = pool.getResource()) {
            Queue queue = new Queue(jedis, "queue1");
            Thread produce = new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < 3; i++) {
                        queue.push("123>>>"+i);
                    }
                }
            };


            Thread consumer = new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < 3; i++) {
                        queue.lope();
                    }
                }
            };

            produce.start();
            consumer.start();



           // Thread.sleep(7000);
           // Thread.interrupted();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
