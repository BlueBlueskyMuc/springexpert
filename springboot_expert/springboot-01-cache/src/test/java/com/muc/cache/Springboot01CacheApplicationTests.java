package com.muc.cache;

import com.muc.cache.mapper.EmployeeMapper;
import com.muc.cache.pojo.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot01CacheApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisTemplate<Object,Employee> empRedisTemplate;


    // 操作String
    @Test
    public void testStringRedisTemplate(){
        stringRedisTemplate.opsForValue().set("stringTemplate", "测试");

    }

    // 操作对象
    @Test
    public void testRedisTemplate(){
        Employee employee = employeeMapper.selectEmpById(1);
        // 如果保存对象,默认使用JdkSerializationRedisSerializer,序列化后的数据保存到redis中
        redisTemplate.opsForValue().set("emp-01",employee);
        // 将数据保存为jason格式
        // 1. 手动转换为json格式再存储
        // 2. redisTemplate默认的序列化
        // 自定义Serializer
        empRedisTemplate.opsForValue().set("emp-01", employee);
    }

    @Test
    public void contextLoads() {
        Employee employee = employeeMapper.selectEmpById(1);
        System.out.println(employee);
    }

}
