package com.cicro.redis.jedis;

import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.Command;

import java.util.List;

public interface RedisCommandInterface  extends Commands {

    /**
     *
     * @param key key
     * @param init 桶的初始化容量
     * @param count 时间窗内可以操作的次数
     * @param period 时间窗
     * @param num 每次漏出的数量
     * @return
     */
    @Command("CL.THROTTLE ?0 ?1 ?2 ?3 ?4")
    List<Object> throttle(String key,Long init,Long count,Long period,Long num);

}
