package com.cicro.redis.jedis;

public class Message {
    private String id;
    private Object data;

    public Message() {
    }

    public Message(String id, Object data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
            "id='" + id + '\'' +
            ", data=" + data +
            '}';
    }
}
