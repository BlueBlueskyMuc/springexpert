package com.muc.cache.config;

import com.muc.cache.pojo.Department;
import com.muc.cache.pojo.Employee;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.net.UnknownHostException;
import java.time.Duration;

@Configuration
public class MyRedisConfig {

    /**
     *
     * @param redisConnectionFactory
     * @return
     * @throws UnknownHostException
     */
    /*
        员工
     */
    // 员工的模板
    @Bean
    public RedisTemplate<Object, Employee> empRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Employee> template = new RedisTemplate<Object, Employee>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Employee> serializer = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
        template.setDefaultSerializer(serializer);
        return template;
    }

    // 员工的缓存管理器
    @Bean
    @Primary // 当有多个缓存管理器的时候需要指定一个默认的缓存管理器
    public RedisCacheManager empRedisCacheManager(RedisConnectionFactory redisConnectionFactory){
        // 初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        // 设置CacheManager的值序列化方式为json序列化
        RedisSerializer<Employee> jsonSerializer = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
        RedisSerializationContext.SerializationPair<Employee> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(jsonSerializer);
        RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair);
        // 设置默认超过期时间是30秒
        // defaultCacheConfig.entryTtl(Duration.ofSeconds(30));

        //设置使用前缀
//        defaultCacheConfig.usePrefix();

        //初始化RedisCacheManager
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }

    /*
        部门
     */
    // 部门的模板
    @Bean
    public RedisTemplate<Object, Department> deptRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Department> template = new RedisTemplate<Object, Department>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Department> serializer = new Jackson2JsonRedisSerializer<Department>(Department.class);
        template.setDefaultSerializer(serializer);
        return template;
    }

    // 部门的缓存管理器
    @Bean
    public RedisCacheManager deptRedisCacheManager(RedisConnectionFactory redisConnectionFactory){
        // 初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        // 设置CacheManager的值序列化方式为json序列化
        RedisSerializer<Department> jsonSerializer = new Jackson2JsonRedisSerializer<Department>(Department.class);
        RedisSerializationContext.SerializationPair<Department> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(jsonSerializer);
        RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair);
        // 设置默认超过期时间是30秒
        // defaultCacheConfig.entryTtl(Duration.ofSeconds(30));

        //设置使用前缀
//        defaultCacheConfig.usePrefix();

        //初始化RedisCacheManager
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }
}
