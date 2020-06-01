package com.yinp.redis.redislettuce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yinp.redis.redislettuce.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class RedisLettuceApplicationTests {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test01() {
        Boolean key = stringRedisTemplate.hasKey("movie");
        System.out.println(key);
        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent("movie", "方世玉");
        System.out.println(aBoolean);
    }

    @Test
    public void test02() {
        String movie = stringRedisTemplate.opsForValue().get("movie");
        System.out.println(movie);
    }

    @Test
    public void test03() {
        User user1 = new User("1", "赵四", 22);
        User user2 = new User("2", "谢广坤", 60);
        redisTemplate.opsForHash().put("user",user1.getId(),user1);
        redisTemplate.opsForHash().put("user",user2.getId(),user2);
    }


    @Test
    public void test06(){
        System.out.println(redisTemplate.opsForHash().get("user", "1"));
        System.out.println(redisTemplate.opsForHash().get("user", "2"));
    }

    @Test
    public void test04() throws JsonProcessingException {
        User user = new User("3", "刘能", 58);
        String s = new ObjectMapper().writeValueAsString(user);
        stringRedisTemplate.opsForValue().set("user:3",s);
        stringRedisTemplate.expire("user:3",60, TimeUnit.HOURS);
    }

    @Test
    public void test05(){
        long s2 = System.currentTimeMillis();
        System.out.println(stringRedisTemplate.opsForHash().hasKey("user:2", "name"));
        log.info("耗时:{}",System.currentTimeMillis()-s2);
        long s3 = System.currentTimeMillis();
        System.out.println(stringRedisTemplate.hasKey("user:1"));
        log.info("耗时:{}",System.currentTimeMillis()-s3);
        long s4 = System.currentTimeMillis();
        System.out.println(stringRedisTemplate.hasKey("user:2"));
        log.info("耗时:{}",System.currentTimeMillis()-s4);
        long s1 = System.currentTimeMillis();
        System.out.println(stringRedisTemplate.opsForHash().hasKey("user:1", "name"));
        log.info("耗时:{}",System.currentTimeMillis()-s1);
    }

    @Test
    public void test07(){
        User user3 = new User("3", "花花", 22);
        User user4 = new User("4", "小童", 23);
        Map<String,User> map =new HashMap<>();
        map.put(user3.getId(),user3);
        map.put(user4.getId(),user4);
        redisTemplate.opsForHash().putAll("user",map);
    }

    @Test
    public void test08(){
        long s2 = System.currentTimeMillis();
        System.out.println(stringRedisTemplate.opsForHash().hasKey("user", "1"));
        log.info("耗时:{}",System.currentTimeMillis()-s2);
        long s1 = System.currentTimeMillis();
        System.out.println(stringRedisTemplate.opsForHash().hasKey("user", "2"));
        log.info("耗时:{}",System.currentTimeMillis()-s1);
    }
}
