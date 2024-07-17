package com.xtwl.mapper;

import com.xtwl.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);


    /**
     * 插入员工数据
     * @param user
     */
    @Insert("insert into user (username, password, email, created, last_modified) " +
            "values " +
            "(#{username},#{password},#{email},#{created},#{last_modified})")
    void insert(User user);
}
