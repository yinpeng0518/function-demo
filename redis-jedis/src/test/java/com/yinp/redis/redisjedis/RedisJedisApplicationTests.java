package com.yinp.redis.redisjedis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@SpringBootTest
class RedisJedisApplicationTests {

    @Autowired
    private JedisPool jedisPool;

    @Test
    void contextLoads() {
        Jedis jedis = this.jedisPool.getResource();
        String set = jedis.set("book", "西游记");
        System.out.println(set);

    }

}
