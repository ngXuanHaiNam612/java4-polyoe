package com.java4.asm2polyoe.mapper;

import com.java4.asm2polyoe.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> findAllByVideoId(@Param("videoId") String videoId);
    Comment findById(@Param("id") Long id);
    void insert(Comment comment);
    void delete(@Param("id") Long id);
}
