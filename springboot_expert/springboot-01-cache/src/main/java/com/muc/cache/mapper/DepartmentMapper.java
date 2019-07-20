package com.muc.cache.mapper;

import com.muc.cache.pojo.Department;
import org.apache.ibatis.annotations.Select;

public interface DepartmentMapper {

    @Select("select * from department where id = #{id}")
    Department getDeptById(Integer id);
}
