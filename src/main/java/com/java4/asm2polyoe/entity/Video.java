package com.java4.asm2polyoe.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    private String id;
    private String title;
    private String poster;
    private Integer views;
    private String description;
    private Boolean active;
}

