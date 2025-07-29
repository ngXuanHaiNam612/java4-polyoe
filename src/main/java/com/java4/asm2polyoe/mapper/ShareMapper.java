package com.java4.asm2polyoe.mapper;

import com.java4.asm2polyoe.entity.Share;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ShareMapper {
    List<Share> findAll();
    Share findById(Long id);
    void insert(Share share);
    void update(Share share);
    void delete(Long id);
}

