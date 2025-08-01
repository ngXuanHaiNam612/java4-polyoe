package com.java4.asm2polyoe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private String videoId;
    private String userId;
    private String content;
    private Date commentDate;
    // Thêm trường transient để chứa thông tin người dùng khi cần hiển thị
    private transient User user;
}

