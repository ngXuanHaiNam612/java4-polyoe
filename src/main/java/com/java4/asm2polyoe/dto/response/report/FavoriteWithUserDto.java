package com.java4.asm2polyoe.dto.response.report;

import com.java4.asm2polyoe.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteWithUserDto {
    private Long id;
    private String userId;
    private String videoId;
    private java.util.Date likeDate;
    private User user;
}

