package com.xtwl.controller;
import com.xtwl.pojo.dto.UserLoginDTO;
import com.xtwl.pojo.dto.UserRegisterDTO;
import com.xtwl.pojo.entity.User;
import com.xtwl.pojo.vo.UserLoginVO;
import com.xtwl.pojo.vo.UserVO;
import com.xtwl.result.Result;
import com.xtwl.service.UserService;
import com.xtwl.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);

        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getUser_id());
        claims.put("username",user.getUsername());
        String token = JwtUtil.createJWT(
                "xtwl",
                7200000,
                claims);

        UserLoginVO userVO = UserLoginVO.builder()
                .user_id(user.getUser_id())
                .userName(user.getUsername())
                .token(token)
                .build();

        return Result.success(userVO);
    }

    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO){
        log.info("用户注册：{}",userRegisterDTO);
        userService.register(userRegisterDTO);
        return Result.success();
    }

    @GetMapping("/me")
    @ApiOperation("查询用户")
    public Result query(@RequestParam String username){
        log.info("查询用户：{}",username);

        User user = userService.query(username);
        UserVO userVO = UserVO.builder()
                .user_id(user.getUser_id())
                .username(user.getUsername())
                .email(user.getEmail())
                .created(user.getCreated())
                .last_modified(user.getLast_modified())
                .build();

        return Result.success(userVO);
    }
}
