package com.xtwl.mapper;

import com.xtwl.pojo.entity.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogMapper {


    /**
     * 创建文章
     * @param blog
     */
    @Insert("insert into blog(post_id,title,content,user_id,created,last_modified)" +
            "values" +
            "(#{post_id},#{title},#{content},#{user_id},#{created},#{last_modified})")
    void insert(Blog blog);


    @Select("select * from blog where user_id = #{user_id}")
    List<Blog> selectBlogByUserId(Long user_id);


    @Select("select * from blog where post_id = #{post_id}")
    Blog selectBlogByPostId(Long post_id);

    @Update("update blog " +
            "set title = #{title}, content = #{content}, last_modified = #{last_modified} " +
            "where post_id = #{post_id} and user_id = #{user_id}")
    void update(Blog blog);

    @Delete("delete from blog " +
            "where post_id = #{post_id} and user_id = #{user_id}")
    void delete(Long post_id,Long user_id);
}
