package com.cicro.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

public class RedisTxTest {

    public static void main(String[] args) {
        System.out.println(saveMoney("zs", 100));
    }

    public static Integer saveMoney(String userId, Integer money) {
        Jedis jedis = new Jedis("192.168.146.128", 6379);
        String s = jedis.get(userId);
        if (s == null) {
            jedis.set(userId, String.valueOf(0));
        }
        String s1 = jedis.get(userId);
        //开启监控
        jedis.watch(userId);
        Transaction multi = jedis.multi();
        int v = Integer.parseInt(s1) + money;
        multi.set(userId, String.valueOf((v)));

        List<Object> exec = multi.exec();
        if (exec != null) {

            return v;
        }

        return null;
    }
}
