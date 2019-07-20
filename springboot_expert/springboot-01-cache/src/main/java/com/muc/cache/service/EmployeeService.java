package com.muc.cache.service;

import com.muc.cache.mapper.EmployeeMapper;
import com.muc.cache.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "emp",cacheManager = "empRedisCacheManager")  //抽取公共的配置
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     *  将方法的运行结果进行缓存,以后要使用相同的数据,可以直接从缓存中获取,不调用方法
     *
     *  CacheManager管理多个cache组件,对缓存的真正CRUD操作在cache中,每一个缓存组件都有组件唯一一个名字
     *  @Cacheable的几个属性：
     *      value/cacheNames：指定缓存组件的名称
     *
     *      key：缓存使用的key,默认使用方法的参数值 id+方法返回值的方式
     *          也可以编写SpEL #id #a0 #p0 #root.args[0] 都表示获取参数id的值
     *          如果我想自定义key如：方法名+muc+参数值
     *          key = "#root.methodName+'muc'+#id"
     *
     *      keyGenerator：key的生成器,可以自己指定key的生成器的组件id
     *          key/keyGenerator二选一使用
     *          使用keyGenerator在自定义config中配置
     *          keyGenerator = "myKeyGenerator"
     *
     *      cacheManager：指定缓存管理器,或者cacheResolver缓存解析器
     *
     *      condition：指定符合条件才缓存
     *      满足id大于1才进行缓存(condition = "#a0>1")
     *
     *      unless：指定符合条件不缓存,可以获取结果再进行判断
     *          #result == null 表示结果为null不缓存
     *      查询返回结果不为空不缓存(unless = "#result!=null")
     *
     *      sync：是否启用异步模式
     * @param id
     * @return
     */
    @Cacheable(/*cacheNames = "emp"*/)
    public Employee getEmpById(Integer id){
        System.out.println("查询"+id+"号员工");
        Employee employee = employeeMapper.selectEmpById(id);
        return employee;
    }

    /**
     * @CachePut：既调用方法,又更新缓存数据,同步更新缓存
     * 一般用于修改某个数据,同时更新缓存
     *
     * 运行时机
     *  1、先掉用目标方法
     *  2、将目标方法的返回结果缓存
     * 测试步骤：
     *  1、先查询1号员工,查询到的结果放入缓存
     *  2、更新1号员工
     *  3、再查询1号员工查看是否是从缓存查询
     *
     *  这样测试会发现执行第3步时还是以前的数据(没有指定key的情况下)
     *      因为都是存储在emp中但是key不一样
     *      第1步查询的key为id
     *      第2步更新的key为employee
     *      所有查询不到更新后的
     *      如果要查询到更新后的要保证cacheNames相同,并且key相同
     *
     *
     * @param employee
     * @return
     */
    @CachePut(/*cacheNames = "emp",*/key = "#employee.id")
    public Employee updateEmp(Employee employee){
        System.out.println("修改"+employee.getId()+"号员工");
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     * @CacheEvict清除缓存
     *      key：需要清除的缓存中存储的key
     *          key = "#id"表示通过id清除
     *      allEntries：清除指定缓存中的所有数据 默认是false表示不清除当前缓存所有数据
     *          allEntries = true表示清除当前缓存所有数据
     *      beforeInvocation = false：在方法执行之后清除缓存
     *          如果运行方法期间出现异常,将不会清除缓存
     *      beforeInvocation = true：在方法执行之前清除缓存
     *          如果运行方法期间出现异常,会清除缓存
     */
    @CacheEvict(value = "emp",beforeInvocation = true)
    public void deleteEmp(Integer id){
        System.out.println("清除"+id+"号员工");
//        employeeMapper.deleteEmp(id);
        int i = 5/0;
    }

    /**
     * @Caching复杂的缓存规则
     *      此处表示使用@Cacheable缓存的名称为emp key为lastName value为Employee
     *      此处表示使用@Cacheable缓存的名称为emp key为id value为Employee
     *      此处表示使用@Cacheable缓存的名称为emp key为email value为Employee
     *  但是当再次查询此方法时还是会调用方法因为有@CachePut (此处只是做演示)
     * @param lastName
     * @return
     */
    @Caching(
        cacheable = {
            @Cacheable(/*value = "emp",*/key = "#lastName")
        },
        put = {
            @CachePut(/*value = "emp",*/key = "#result.id"),
            @CachePut(/*value = "emp",*/key = "#result.email")
        }
    )
    public Employee selectEmpByLastName(String lastName){
        System.out.println("通过"+lastName+"名字查询");
       return employeeMapper.selectEmpByLastName(lastName);
    }
}
