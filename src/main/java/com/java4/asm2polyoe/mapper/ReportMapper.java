package com.java4.asm2polyoe.mapper;

import com.java4.asm2polyoe.dto.response.report.VideoFavoriteStatsDto;
import com.java4.asm2polyoe.entity.Favorite;
import com.java4.asm2polyoe.entity.Share;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportMapper {
    List<VideoFavoriteStatsDto> countFavoritesByVideo();
    List<Favorite> findFavoritesByVideoId(@Param("videoId") String videoId);
    List<Share> findSharesByVideoId(@Param("videoId") String videoId);

    // General stats
    Long countAllUsers();
    Long countAllVideos();
    Long countAllFavorites();
    Long countAllShares();
    Long sumAllVideoViews();
}

