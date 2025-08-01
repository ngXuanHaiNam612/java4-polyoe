package com.java4.asm2polyoe.controller;

import com.java4.asm2polyoe.dto.response.ApiResponse;
import com.java4.asm2polyoe.entity.Video;
import com.java4.asm2polyoe.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/{id}/view")
    public ApiResponse<Void> incrementViews(@PathVariable String id) {
        videoService.incrementViews(id);
        return ApiResponse.<Void>builder()
                .status(200)
                .success(true)
                .message("Views incremented")
                .build();
    }

    @GetMapping("/top")
    public ApiResponse<List<Video>> getTopVideos(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "6") int size) {
        List<Video> videos = videoService.getTopVideos(page, size);
        return ApiResponse.<List<Video>>builder()
                .status(200)
                .success(true)
                .data(videos)
                .message("Fetched top videos")
                .build();
    }

    // NEW: Endpoint để lấy các video đang hoạt động và nổi bật
    @GetMapping("/top-active")
    public ApiResponse<List<Video>> getTopActiveVideos(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "6") int size) {
        List<Video> videos = videoService.getTopActiveVideos(page, size);
        return ApiResponse.<List<Video>>builder()
                .status(200)
                .success(true)
                .data(videos)
                .message("Fetched top active videos")
                .build();
    }
}
