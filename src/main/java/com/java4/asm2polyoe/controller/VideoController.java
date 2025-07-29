package com.java4.asm2polyoe.controller;

import com.java4.asm2polyoe.dto.response.ApiResponse;
import com.java4.asm2polyoe.entity.Video;
import com.java4.asm2polyoe.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    public ApiResponse<List<Video>> getAllVideos() {
        List<Video> videos = videoService.findAll();
        return ApiResponse.<List<Video>>builder()
                .status(200)
                .success(true)
                .data(videos)
                .message("Fetched all videos")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<Video> getVideoById(@PathVariable String id) {
        Video video = videoService.findById(id);
        return ApiResponse.<Video>builder()
                .status(200)
                .success(true)
                .data(video)
                .message("Video found")
                .build();
    }

    @PostMapping
    public ApiResponse<Video> createVideo(@RequestBody Video video) {
        Video createdVideo = videoService.save(video);
        return ApiResponse.<Video>builder()
                .status(201)
                .success(true)
                .data(createdVideo)
                .message("Video created")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Video> updateVideo(@PathVariable String id, @RequestBody Video video) {
        Video updatedVideo = videoService.update(id, video);
        return ApiResponse.<Video>builder()
                .status(200)
                .success(true)
                .data(updatedVideo)
                .message("Video updated")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteVideo(@PathVariable String id) {
        videoService.delete(id);
        return ApiResponse.<Void>builder()
                .status(204)
                .success(true)
                .message("Video deleted")
                .build();
    }
}
