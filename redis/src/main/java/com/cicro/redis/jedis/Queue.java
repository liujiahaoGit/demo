package com.cicro.redis.jedis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class Queue {

    private Jedis jedis;

    private String queue;

    public Queue(Jedis jedis, String queue) {
        this.jedis = jedis;
        this.queue = queue;
    }

    /**
     * 入队
     *
     * @param data 需要发送的消息
     */
    public void push(Object data) {
        try {

            String id = UUID.randomUUID().toString();
            Message message = new Message(id, data);
            String msg = new ObjectMapper().writeValueAsString(message); //序列化消息对象
            jedis.zadd(queue, System.currentTimeMillis() + 5000, msg); //发送消息 score 延迟 5 秒
            System.out.println(new Date() + ">>>发送消息>>>" + msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 出队
     */
    public void lope() {

        while (!Thread.interrupted()) {
            //读取 score 在 0 到当前时间戳之间的消息
            Set<String> set = jedis.zrangeByScore(queue, 0, System.currentTimeMillis(), 0, 1);
            if (set.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                continue;
            }
            Iterator<String> iterator = set.iterator();
                String next = iterator.next();
                if (jedis.zrem(queue, next) > 0) {
                    try {
                        Message message = new ObjectMapper().readValue(next, Message.class);
                        System.out.println(new Date() + ">>>message >>> " + message);

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }

        }

    }
}
