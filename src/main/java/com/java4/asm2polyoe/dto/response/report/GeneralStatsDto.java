package com.java4.asm2polyoe.dto.response.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralStatsDto {
    private Long totalUsers;
    private Long totalVideos;
    private Long totalFavorites;
    private Long totalShares;
    private Long totalViews;
}

