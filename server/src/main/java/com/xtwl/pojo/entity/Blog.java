package com.xtwl.pojo.entity;

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
public class Blog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long post_id;

    private String title;

    private String content;

    private Long user_id;

    private LocalDateTime created;

    private LocalDateTime last_modified;
}
