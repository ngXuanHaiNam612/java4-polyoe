package com.java4.asm2polyoe.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    private String id;
    private String title;
    private String poster;
    private String href;
    private String description;
    private Long views;
    private Boolean active;
}


