package com.java4.asm2polyoe.entity;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private Long id;
    private String userId;
    private String videoId;
    private Date likeDate;
}
