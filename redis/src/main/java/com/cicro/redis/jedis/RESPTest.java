package com.cicro.redis.jedis;

import java.io.IOException;
import java.net.Socket;

public class RESPTest {

    private Socket socket;

    public RESPTest() {
        try {
            socket = new Socket("192.168.13.128", 6379);
        } catch (IOException e) {
            System.out.println("redis连接异常");
            e.printStackTrace();
        }
    }

    /**
     *  执行 Redis 中的 set 命令 [set,key,value]
     * @param type
     * @param key
     * @param value
     * @return
     * @throws IOException
     */
    public String set(String type, String key, String value) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("*3")
            .append("\r\n")
            .append("$")
            .append(type.length())
            .append("\r\n")
            .append(type)
            .append("\r\n")
            .append("$")
            .append(key.getBytes().length)
            .append("\r\n")
            .append(key)
            .append("\r\n")
            .append("$")
            .append(value.getBytes().length)
            .append("\r\n")
            .append(value)
            .append("\r\n");
        byte[] bytes = new byte[1024];
        socket.getOutputStream().write(sb.toString().getBytes());
        socket.getInputStream().read(bytes);
        System.out.println("sb.toString() = " + sb.toString());
        return new String(bytes);
    }

    /**
     * Redis 中的 get 命令 [get,key]
     * @param key
     * @return
     * @throws IOException
     */
    public String get(String key) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("*2")
            .append("\r\n")
            .append("$")
            .append("get".length())
            .append("\r\n")
            .append("get")
            .append("\r\n")
            .append("$")
            .append(key.getBytes().length)
            .append("\r\n")
            .append(key)
            .append("\r\n");
        socket.getOutputStream().write(sb.toString().getBytes());
        byte[] bytes = new byte[1024];
        socket.getInputStream().read(bytes);
        System.out.println("sb.toString() = " + sb.toString());
        return new String(bytes);
    }

    public static void main(String[] args) throws IOException {
        RESPTest jedisTest = new RESPTest();
        String set = jedisTest.set("set", "测试", "v1");
        String get = jedisTest.get("测试");
        System.out.println("set = " + set);
        System.out.println("get = " + get);
    }
}
