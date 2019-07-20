package com.muc.cache.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class MyCacheConfig {

    // 因为默认是以返回值作为类型,方法名作为id所有key给@Bean指定value来指定id值
    @Bean(value = "myKeyGenerator")
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getName()+"-->Muc";
            }
        };
    }
}
