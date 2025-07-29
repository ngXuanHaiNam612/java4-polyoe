package com.java4.asm2polyoe.mapper;

import com.java4.asm2polyoe.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteMapper {
    List<Favorite> findAll();
    Favorite findById(Long id);
    void insert(Favorite favorite);
    void update(Favorite favorite);
    void delete(Long id);
}

