package com.xtwl.controller;
import com.xtwl.pojo.dto.BlogInsertDTO;
import com.xtwl.pojo.dto.BlogUpdateDTO;
import com.xtwl.pojo.entity.Blog;
import com.xtwl.result.Result;
import com.xtwl.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Api(tags = "Blog相关接口")
@Slf4j
public class BolgController {
    @Autowired
    private BlogService blogService;

    /**
     * 创建文章
     * @param blogInsertDTO
     * @return
     */
    @PostMapping
    @ApiOperation("创建文章")
    public Result insert(@RequestBody BlogInsertDTO blogInsertDTO){
        log.info("创建新文章：{}",blogInsertDTO);
        blogService.insert(blogInsertDTO);
        return Result.success();
    }

    /**
     * 根据用户id查询其所有文章
     * @param user_id
     * @return
     */
    @GetMapping
    @ApiOperation("查询用户文章")
    public Result<List<Blog>> selectBlogsByUserId(@RequestParam("uid") Long user_id){
        log.info("根据用户id查询其所有文章:{}",user_id);
        List<Blog> blogs = blogService.selectBlogByUserId(user_id);
        return Result.success(blogs);
    }

    @GetMapping("{id}")
    @ApiOperation("根据文章id查询文章")
    public Result<Blog> selectBlogByPostId(@PathVariable("id") Long post_id){
        log.info("根据文章id查询文章：{}",post_id);
        Blog blog = blogService.selectBlogByPostId(post_id);
        return Result.success(blog);
    }

    @PutMapping("{id}")
    @ApiOperation("更新文章")
    public Result update(@PathVariable("id") Long post_id,@RequestBody BlogUpdateDTO blogUpdateDTO){
        log.info("更新文章：{}",post_id);
        log.info("更新内容：{}",blogUpdateDTO);
        blogUpdateDTO.setPost_id(post_id);

        blogService.update(blogUpdateDTO);
        return Result.success();
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除文章")
    public Result delete(@PathVariable("id") Long post_id){
        log.info("删除文章：{}",post_id);
        blogService.delete(post_id);
        return Result.success();
    }

}
