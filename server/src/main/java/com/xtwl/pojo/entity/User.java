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
public class User implements Serializable {

    private Long user_id;

    private String username;

    private String password;

    private String email;

    private LocalDateTime created;

    private LocalDateTime last_modified;


}
