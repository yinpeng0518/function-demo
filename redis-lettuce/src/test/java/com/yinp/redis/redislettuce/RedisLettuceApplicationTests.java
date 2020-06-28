package com.yinp.redis.redislettuce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yinp.redis.redislettuce.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@SpringBootTest
class RedisLettuceApplicationTests {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test01() {
        redisTemplate.opsForValue().setIfAbsent("lock", 1, 100, TimeUnit.MILLISECONDS);
        redisTemplate.multi();
        redisTemplate.exec();
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
        redisTemplate.opsForHash().put("user", user1.getId(), user1);
        redisTemplate.opsForHash().put("user", user2.getId(), user2);
    }


    @Test
    public void test06() {
        System.out.println(redisTemplate.opsForHash().get("user", "1"));
        System.out.println(redisTemplate.opsForHash().get("user", "2"));
    }

    @Test
    public void test04() throws JsonProcessingException {
        User user = new User("3", "刘能", 58);
        String s = new ObjectMapper().writeValueAsString(user);
        stringRedisTemplate.opsForValue().set("user:3", s);
        stringRedisTemplate.expire("user:3", 60, TimeUnit.HOURS);
    }

    @Test
    public void test05() {
        long s2 = System.currentTimeMillis();
        System.out.println(stringRedisTemplate.opsForHash().hasKey("user:2", "name"));
        log.info("耗时:{}", System.currentTimeMillis() - s2);
        long s3 = System.currentTimeMillis();
        System.out.println(stringRedisTemplate.hasKey("user:1"));
        log.info("耗时:{}", System.currentTimeMillis() - s3);
        long s4 = System.currentTimeMillis();
        System.out.println(stringRedisTemplate.hasKey("user:2"));
        log.info("耗时:{}", System.currentTimeMillis() - s4);
        long s1 = System.currentTimeMillis();
        System.out.println(stringRedisTemplate.opsForHash().hasKey("user:1", "name"));
        log.info("耗时:{}", System.currentTimeMillis() - s1);
    }

    @Test
    public void test07() {
        User user3 = new User("3", "花花", 22);
        User user4 = new User("4", "小童", 23);
        Map<String, User> map = new HashMap<>();
        map.put(user3.getId(), user3);
        map.put(user4.getId(), user4);
        redisTemplate.opsForHash().putAll("user", map);
    }

    @Test
    public void test08() {
        long s2 = System.currentTimeMillis();
        System.out.println(stringRedisTemplate.opsForHash().hasKey("user", "1"));
        log.info("耗时:{}", System.currentTimeMillis() - s2);
        long s1 = System.currentTimeMillis();
        System.out.println(stringRedisTemplate.opsForHash().hasKey("user", "2"));
        log.info("耗时:{}", System.currentTimeMillis() - s1);
    }

    @Test
    public void test09() {

        System.out.println(Long.MAX_VALUE);
        System.out.println(Long.MAX_VALUE - 10);
    }

    @Test
    public void test10() {
        Long t1 = redisTemplate.opsForHyperLogLog().add("20200605", 001, 002, 005, 004, 001, 002, 003, 007);
        Long t2 = redisTemplate.opsForHyperLogLog().add("20200606", 001, 002, 005, 004, 001, 002, 003, "008");
        System.out.println("20200605   => " + t1);  //1
        System.out.println("20200606   => " + t2);  //1

        Long size1 = redisTemplate.opsForHyperLogLog().size("20200605");
        Long size2 = redisTemplate.opsForHyperLogLog().size("20200606");
        System.out.println("size1   => " + size1);  //6
        System.out.println("size2   => " + size2);  //6

        Long union = redisTemplate.opsForHyperLogLog().union("05-06", "20200605", "20200606");
        System.out.println("union  => " + union);   //7
    }

    @Test
    public void test11() {
        redisTemplate.opsForValue().setBit("Time-5", 0, true);
        redisTemplate.opsForValue().setBit("Time-5", 3, true);
        redisTemplate.opsForValue().setBit("Time-5", 5, true);
        redisTemplate.opsForValue().setBit("Time-5", 8, true);
        redisTemplate.opsForValue().setBit("Time-5", 9, true);
        redisTemplate.opsForValue().setBit("Time-5", 10, true);

        redisTemplate.opsForValue().setBit("Time-6", 1, true);
        redisTemplate.opsForValue().setBit("Time-6", 2, true);
        redisTemplate.opsForValue().setBit("Time-6", 5, true);
        redisTemplate.opsForValue().setBit("Time-6", 6, true);
        redisTemplate.opsForValue().setBit("Time-6", 7, true);
        redisTemplate.opsForValue().setBit("Time-6", 9, true);

        System.out.println(redisTemplate.opsForValue().getBit("Time-5", 7));
        System.out.println(redisTemplate.opsForValue().getBit("Time-5", 8));

        System.out.println(redisTemplate.opsForValue().getBit("Time-6", 2));
        System.out.println(redisTemplate.opsForValue().getBit("Time-6", 3));

        List<Long> longs = redisTemplate.opsForValue().bitField("Time-5", BitFieldSubCommands.create());
        longs.forEach(System.out::println);
    }

    public static void main(String[] args) {
        BigDecimal decimal = new BigDecimal("5000").setScale(2, RoundingMode.DOWN);
        System.out.println(decimal);
    }

    @Test
    public void test12() {
        String str1 = "1e2b5f331A4E";
        String str2 = "1223.5f33.1A4E";
        String str3 = "1e:2b:5f:33:1A:4E";
        String str4 = "1e-2b-5f-33-1A-4E";

        String regex = "^([A-Fa-f0-9]{2})[.:-]?([A-Fa-f0-9]{2})[.:-]?([A-Fa-f0-9]{2})[.:-]?([A-Fa-f0-9]{2})[.:-]?([A-Fa-f0-9]{2})[.:-]?([A-Fa-f0-9]{2})$";
        System.out.println(str1.matches(regex));
        System.out.println(str2.matches(regex));
        System.out.println(str3.matches(regex));
        System.out.println(str4.matches(regex));

        if (str1.matches(regex)) {
            System.out.println(str1.replaceAll(regex, "$1-$2-$3-$4-$5-$6").toUpperCase());
        }

        if (str2.matches(regex)) {
            System.out.println(str2.replaceAll(regex, "$1-$2-$3-$4-$5-$6").toUpperCase());
        }

        if (str3.matches(regex)) {
            System.out.println(str3.replaceAll(regex, "$1-$2-$3-$4-$5-$6").toUpperCase());
        }

        if (str4.matches(regex)) {
            System.out.println(str4.replaceAll(regex, "$1-$2-$3-$4-$5-$6").toUpperCase());
        }
    }

    @Test
    public void test13() {
        String str = "n13977777777s18911111111你好15988888888hha0955-7777777sss0775-6678111";
        Pattern pattern = Pattern.compile("((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println("查询到一个符合的手机号码：" + matcher.group());
        }
    }

    @Test
    public void test14() {
        int sumOdd = 0;
        int sumEven = 0;
        String number = "6214832707610965";
        int length = number.length();
        int[] wei = new int[length];
        for (int i = 0; i < number.length(); i++) {
            wei[i] = Integer.parseInt(number.substring(length - i - 1, length - i));// 从最末一位开始提取，每一位上的数值
            System.out.println("第" + i + "位数字是：" + wei[i]);
        }
        for (int i = 0; i < length / 2; i++) {
            sumOdd += wei[2 * i];
            if ((wei[2 * i + 1] * 2) > 9)
                wei[2 * i + 1] = wei[2 * i + 1] * 2 - 9;
            else
                wei[2 * i + 1] *= 2;
            sumEven += wei[2 * i + 1];
        }
        System.out.println("奇数位的和是：" + sumOdd);
        System.out.println("偶数位的和是：" + sumEven);
        if ((sumOdd + sumEven) % 10 == 0)
            System.out.println("Recept.");
        else
            System.out.println("Can not recept.");
    }
}

