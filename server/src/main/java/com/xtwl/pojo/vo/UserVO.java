package com.xtwl.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户查询返回的数据格式")
public class UserVO implements Serializable {
    private Long user_id;

    private String username;

    private String email;

    private LocalDateTime created;

    private LocalDateTime last_modified;
}
