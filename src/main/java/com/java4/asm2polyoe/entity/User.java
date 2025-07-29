package com.java4.asm2polyoe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String password;
    private String fullname;
    private String email;
    private Boolean admin;
}