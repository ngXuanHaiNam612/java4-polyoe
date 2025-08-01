package com.java4.asm2polyoe.service;

import com.java4.asm2polyoe.entity.ViewHistory;

import java.util.List;

public interface ViewHistoryService {
    List<ViewHistory> findAll();
    ViewHistory findById(Long id);

    ViewHistory findByUserIdAndVideoId(String userId, String videoId);

    ViewHistory save(ViewHistory history);
    ViewHistory update(Long id, ViewHistory history);
    void delete(Long id);
}

