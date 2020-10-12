package com.cicro.redis.jedis;

import io.rebloom.client.Client;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;

public class BloomFilterTest {
    public static void main(String[] args) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(1000);
        config.setMaxIdle(300);
        config.setMaxWaitMillis(30000);
        config.setTestOnBorrow(true);
        JedisPool pool = new JedisPool(config, "192.168.228.128", 6379, 10000, "123");

        Client client=new Client(pool);
        /*for (int i = 0; i < 100000; i++) {

            client.add("name","test>>"+i);
        }*/
        boolean exists = client.exists("name", "test>>999999999");
        System.out.println("exists = " + exists); //true 误判 实则不存在
    }
}
