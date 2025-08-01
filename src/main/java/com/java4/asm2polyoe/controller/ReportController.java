package com.java4.asm2polyoe.controller;

import com.java4.asm2polyoe.dto.response.ApiResponse;
import com.java4.asm2polyoe.dto.response.report.FavoriteWithUserDto;
import com.java4.asm2polyoe.dto.response.report.GeneralStatsDto;
import com.java4.asm2polyoe.dto.response.report.ShareWithUserDto;
import com.java4.asm2polyoe.dto.response.report.VideoFavoriteStatsDto;
import com.java4.asm2polyoe.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/favorites-stats")
    public ApiResponse<List<VideoFavoriteStatsDto>> getFavoriteStats() {
        List<VideoFavoriteStatsDto> stats = reportService.getFavoriteStats();
        return ApiResponse.<List<VideoFavoriteStatsDto>>builder()
                .status(200)
                .success(true)
                .data(stats)
                .message("Fetched favorite statistics successfully")
                .build();
    }

    @GetMapping("/favorites-by-video/{videoId}")
    public ApiResponse<List<FavoriteWithUserDto>> getFavoritesByVideo(@PathVariable String videoId) {
        List<FavoriteWithUserDto> favorites = reportService.getFavoritesByVideo(videoId);
        return ApiResponse.<List<FavoriteWithUserDto>>builder()
                .status(200)
                .success(true)
                .data(favorites)
                .message("Fetched favorites by video successfully")
                .build();
    }

    @GetMapping("/shares-by-video/{videoId}")
    public ApiResponse<List<ShareWithUserDto>> getSharesByVideo(@PathVariable String videoId) {
        List<ShareWithUserDto> shares = reportService.getSharesByVideo(videoId);
        return ApiResponse.<List<ShareWithUserDto>>builder()
                .status(200)
                .success(true)
                .data(shares)
                .message("Fetched shares by video successfully")
                .build();
    }

    @GetMapping("/general-stats")
    public ApiResponse<GeneralStatsDto> getGeneralStats() {
        GeneralStatsDto stats = reportService.getGeneralStats();
        return ApiResponse.<GeneralStatsDto>builder()
                .status(200)
                .success(true)
                .data(stats)
                .message("Fetched general statistics successfully")
                .build();
    }
}

