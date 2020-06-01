package com.yinp.redis.redisjedis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.UUID;


@SpringBootTest
class RedisJedisApplicationTests {

    @Autowired
    private JedisPool jedisPool;

    private Jedis jedis;

    @BeforeEach
    public void before() {
        jedis = this.jedisPool.getResource();
    }

    @Test
    void contextLoads() {
        String set = jedis.set("book", "西游记");
        System.out.println(set);
    }

    @Test
    public void test01() {
        String name = jedis.get("name");
        System.out.println(name);
    }

    @Test
    public void test02() {
        String id = UUID.randomUUID().toString();
        String key = "user:" + id;
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "赵四");
        map.put("age", "23");
        map.put("id", id);
        String hmset = jedis.hmset(key, map);
        System.out.println(hmset);
    }

}
