package com.xtwl.pojo.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "用户注册时传递的数据模型")
public class UserRegisterDTO implements Serializable {

    private String username;

    private String password;

    private String email;


}
