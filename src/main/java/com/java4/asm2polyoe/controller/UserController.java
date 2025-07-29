package com.java4.asm2polyoe.controller;


import com.java4.asm2polyoe.dto.response.ApiResponse;
import com.java4.asm2polyoe.entity.User;
import com.java4.asm2polyoe.service.UseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UseService userService;

    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ApiResponse.<List<User>>builder()
                .status(200)
                .success(true)
                .data(users)
                .message("Fetched all users")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable String id) {
        User user = userService.findById(id);
        return ApiResponse.<User>builder()
                .status(200)
                .success(true)
                .data(user)
                .message("User found")
                .build();
    }

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) {
        User createdUser = userService.save(user);
        return ApiResponse.<User>builder()
                .status(201)
                .success(true)
                .data(createdUser)
                .message("User created")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable String id, @RequestBody User user) {
        User updatedUser = userService.update(id, user);
        return ApiResponse.<User>builder()
                .status(200)
                .success(true)
                .data(updatedUser)
                .message("User updated")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable String id) {
        userService.delete(id);
        return ApiResponse.<Void>builder()
                .status(204)
                .success(true)
                .message("User deleted")
                .build();
    }
}

