package com.xtwl.service.impl;

import com.xtwl.mapper.BlogMapper;
import com.xtwl.pojo.dto.BlogInsertDTO;
import com.xtwl.pojo.dto.BlogUpdateDTO;
import com.xtwl.pojo.entity.Blog;
import com.xtwl.service.BlogService;
import com.xtwl.utils.UserContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    public void insert(BlogInsertDTO blogInsertDTO) {
        Blog blog = new Blog();

        BeanUtils.copyProperties(blogInsertDTO,blog);
        blog.setUser_id(UserContext.getCurrentId());

        blog.setCreated(LocalDateTime.now());
        blog.setLast_modified(LocalDateTime.now());
        blogMapper.insert(blog);
    }


    public List<Blog> selectBlogByUserId(Long user_id) {
        List<Blog> list= blogMapper.selectBlogByUserId(user_id);
        return list;

    }

    public Blog selectBlogByPostId(Long post_id) {
        Blog blog = blogMapper.selectBlogByPostId(post_id);
        return blog;
    }

    public void update(BlogUpdateDTO blogUpdateDTO) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogUpdateDTO,blog);
        blog.setUser_id(UserContext.getCurrentId());
        blog.setLast_modified(LocalDateTime.now());
        blogMapper.update(blog);
    }


    public void delete(Long post_id) {
        Long user_id = UserContext.getCurrentId();
        blogMapper.delete(post_id,user_id);
    }
}
