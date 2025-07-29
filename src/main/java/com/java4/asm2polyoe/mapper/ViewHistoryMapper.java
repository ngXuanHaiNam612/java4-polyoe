package com.java4.asm2polyoe.mapper;

import com.java4.asm2polyoe.entity.ViewHistory;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ViewHistoryMapper {
    List<ViewHistory> findAll();
    ViewHistory findById(Long id);
    void insert(ViewHistory viewHistory);
    void update(ViewHistory viewHistory);
    void delete(Long id);
}

