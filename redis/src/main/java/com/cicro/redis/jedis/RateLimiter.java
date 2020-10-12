package com.cicro.redis.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class RateLimiter {
    private Jedis jedis;

    public RateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 限流方法
     *
     * @param user     限流用户
     * @param action   具体操作
     * @param period   限流的时间窗口
     * @param maxCount 限流次数
     * @return
     */
    public boolean isAllowd(String user, String action, int period, int maxCount) {
        //数据用zset保存,按照一定规则生成一个key
        String key = user + "-" + action;

        //当前时间戳
        long nowTime = System.currentTimeMillis();

        //建立管道
        Pipeline pipelined = jedis.pipelined();
        pipelined.multi();

        //将当前操作保存下来
        pipelined.zadd(key, nowTime, String.valueOf(nowTime));

        //移除时间窗之外的数据
        pipelined.zrangeByScore(key, 0, nowTime - period * 1000);
        //给当前key设置过期时间
        pipelined.expire(key, period + 1);

        //统计剩下的key
        Response<Long> response = pipelined.zcard(key);

        //关闭管道
        pipelined.exec();
        pipelined.close();

        return response.get() <= maxCount;
    }

    public static void main(String[] args) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(1000);
        config.setMaxIdle(300);
        config.setMaxWaitMillis(30000);
        config.setTestOnBorrow(true);
        JedisPool pool = new JedisPool(config, "192.168.228.128", 6379, 10000, "123");
        Jedis jedis = pool.getResource();
        RateLimiter limiter=new RateLimiter(jedis);
        for (int i = 0; i < 1000; i++) {

            boolean allowd = limiter.isAllowd("张三", "play game", 5, 3);

            System.out.println("allowd = " + allowd);
        }
    }
}
