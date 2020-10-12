package com.cicro.redis.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class ClusterTest {
    public static void main(String[] args) {
        Set<HostAndPort> node=new HashSet<>();
        node.add(new HostAndPort("192.168.120.128",7001));
        node.add(new HostAndPort("192.168.120.128",7002));
        node.add(new HostAndPort("192.168.120.128",7003));
        node.add(new HostAndPort("192.168.120.128",7004));
        node.add(new HostAndPort("192.168.120.128",7005));
        node.add(new HostAndPort("192.168.120.128",7006));
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        //连接池最大连接数
        config.setMaxTotal(1000);
        //最大空闲数
        config.setMaxIdle(300);
        //最大等待时间
        config.setMaxWaitMillis(30000);
        //在空闲时检查有效性
        config.setTestOnBorrow(true);
        JedisCluster jedisCluster=new JedisCluster(node,10000,5,3,"123",config);
        String set = jedisCluster.set("name", "zs");
        System.out.println("set = " + set);
        String name = jedisCluster.get("name");
        System.out.println("name = " + name);
    }
}
