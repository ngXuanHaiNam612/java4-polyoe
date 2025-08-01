package com.java4.asm2polyoe.service;

import com.java4.asm2polyoe.entity.Video;
import java.util.List;

public interface VideoService {
    List<Video> findAll();
    Video findById(String id);
    List<Video> findAllByViewsDesc();
    Video save(Video video);
    Video update(String id, Video video);
    void delete(String id);
    void incrementViews(String id);
    List<Video> getTopVideos(int page, int size);
    List<Video> getTopActiveVideos(int page, int size); // Thêm phương thức này
}

