package com.java4.asm2polyoe.service;

import com.java4.asm2polyoe.dto.response.report.FavoriteWithUserDto;
import com.java4.asm2polyoe.dto.response.report.GeneralStatsDto;
import com.java4.asm2polyoe.dto.response.report.ShareWithUserDto;
import com.java4.asm2polyoe.dto.response.report.VideoFavoriteStatsDto;

import java.util.List;

public interface ReportService {
    List<VideoFavoriteStatsDto> getFavoriteStats();
    List<FavoriteWithUserDto> getFavoritesByVideo(String videoId);
    List<ShareWithUserDto> getSharesByVideo(String videoId);
    GeneralStatsDto getGeneralStats();
}
