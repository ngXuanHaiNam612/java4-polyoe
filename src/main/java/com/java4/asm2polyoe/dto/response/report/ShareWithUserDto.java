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
public class ShareWithUserDto {
    private Long id;
    private String userId;
    private String videoId;
    private String emails;
    private java.util.Date shareDate;
    private User user; // Thông tin người dùng liên quan
}

