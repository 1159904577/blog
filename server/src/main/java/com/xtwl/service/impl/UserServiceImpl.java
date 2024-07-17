package com.xtwl.service.impl;

import com.xtwl.mapper.UserMapper;
import com.xtwl.pojo.dto.UserLoginDTO;
import com.xtwl.pojo.dto.UserRegisterDTO;
import com.xtwl.pojo.entity.User;
import com.xtwl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.getByUsername(username);
        System.out.println(user);
        if (user == null) {
            throw new RuntimeException("账户不存在");
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        return user;

    }

    public void register(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();

        User user = userMapper.getByUsername(username);
        if (user != null) {
            throw new RuntimeException("用户名已存在");
        }
        User newUser = new User();

        newUser.setUsername(username);

        String password = userRegisterDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        newUser.setPassword(password);

        String email = userRegisterDTO.getEmail();
        newUser.setEmail(email);

        newUser.setCreated(LocalDateTime.now());
        newUser.setLast_modified(LocalDateTime.now());
        userMapper.insert(newUser);
    }


    public User query(String username) {
        User user = userMapper.getByUsername(username);
        return user;
    }


}
