package com.muc.cache.service;

import com.muc.cache.mapper.DepartmentMapper;
import com.muc.cache.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheManager = "deptRedisCacheManager")
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Qualifier("deptRedisCacheManager") //明确的指定deptRedisCacheManager的缓存管理器
    @Autowired
    RedisCacheManager deptRedisCacheManager;

//    @Cacheable(cacheNames = "dept")
//    public Department getDetpById(Integer id){
//        return departmentMapper.getDeptById(id);
//    }

    // 以代码的方式实现缓存,使用缓存管理器,进行API操作
    public Department getDetpById(Integer id){
        Department department = departmentMapper.getDeptById(id);

        // 获取某个缓存
        Cache dept = deptRedisCacheManager.getCache("dept");
        // 添加缓存
        dept.put("dept:code", department);
//        dept.evict("dept:code"); // 删除一个key
        return department;
    }
}
