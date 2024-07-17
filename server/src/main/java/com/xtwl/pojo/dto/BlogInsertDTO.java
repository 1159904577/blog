package com.xtwl.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@ApiModel(description = "创建文章时传递的数据模型")
public class BlogInsertDTO implements Serializable {

    private String title;

    private String content;

}
