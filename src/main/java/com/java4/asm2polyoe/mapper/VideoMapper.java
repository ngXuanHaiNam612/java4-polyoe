package com.java4.asm2polyoe.mapper;

import com.java4.asm2polyoe.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoMapper {
    List<Video> findAll();
    Video findById(@Param("id") String id);
    List<Video> findAllByViewsDesc();
    List<Video> findAllActive(); // Thêm phương thức này
    void insert(Video video);
    void update(Video video);
    void delete(@Param("id") String id);
}


