package com.java4.asm2polyoe.mapper;

import com.java4.asm2polyoe.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface VideoMapper {
    List<Video> findAll();
    Video findById(String id);
    void insert(Video video);
    void update(Video video);
    void delete(String id);
}

