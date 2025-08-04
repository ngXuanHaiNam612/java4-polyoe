package com.java4.asm2polyoe.entity;

import com.fasterxml.jackson.annotation.JsonProperty; // Import này
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Cho phép ghi từ JSON, nhưng không đọc ra JSON
    private String password;
    private String fullname;
    private String email;
    private Boolean admin;

    private String bio;
}


