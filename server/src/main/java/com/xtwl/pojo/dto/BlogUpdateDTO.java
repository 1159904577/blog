package com.xtwl.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "更新文章时传递的数据模型")
public class BlogUpdateDTO implements Serializable {

    private Long post_id;

    private String title;

    private String content;

}
