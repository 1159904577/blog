package com.xtwl.service;

import com.xtwl.pojo.dto.BlogInsertDTO;
import com.xtwl.pojo.dto.BlogUpdateDTO;
import com.xtwl.pojo.entity.Blog;

import java.util.List;

public interface BlogService {

    void insert(BlogInsertDTO blogInsertDTO);

    List<Blog> selectBlogByUserId(Long user_id);

    Blog selectBlogByPostId(Long post_id);

    void update(BlogUpdateDTO blogUpdateDTO);

    void delete(Long post_id);
}
