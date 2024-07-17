package com.xtwl.service;

import com.xtwl.pojo.dto.UserLoginDTO;
import com.xtwl.pojo.dto.UserRegisterDTO;
import com.xtwl.pojo.entity.User;

public interface UserService {

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    /**
     * 用户注册
     * @param userRegisterDTO
     */
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * 查询用户
     * @param username
     */
    User query(String username);
}
