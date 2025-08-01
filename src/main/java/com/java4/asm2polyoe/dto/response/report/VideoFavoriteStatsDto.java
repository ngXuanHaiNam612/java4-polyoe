package com.java4.asm2polyoe.dto.response.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoFavoriteStatsDto {
    private String videoId;
    private String title;
    private String poster;
    private Long likeCount;
}

