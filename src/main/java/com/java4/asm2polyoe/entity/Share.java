package com.java4.asm2polyoe.entity;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Share {
    private Long id;
    private String userId;
    private String videoId;
    private String emails;
    private Date shareDate;
}
