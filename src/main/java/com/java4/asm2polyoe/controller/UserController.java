package com.java4.asm2polyoe.controller;

import com.java4.asm2polyoe.dto.response.ApiResponse;
import com.java4.asm2polyoe.entity.User;
import com.java4.asm2polyoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // --- Các phương thức CRUD hiện có ---
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

    // --- Các phương thức xác thực mới ---
    @PostMapping("/login")
    public ApiResponse<User> login(@RequestBody User loginRequest) {
        User user = userService.login(loginRequest.getId(), loginRequest.getPassword());
        return ApiResponse.<User>builder()
                .status(200)
                .success(true)
                .data(user)
                .message("Login successful")
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody User registerRequest) {
        User newUser = userService.register(registerRequest);
        return ApiResponse.<User>builder()
                .status(201)
                .success(true)
                .data(newUser)
                .message("Registration successful")
                .build();
    }

    @PostMapping("/forgot-password")
    public ApiResponse<Void> forgotPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        userService.forgotPassword(email);
        return ApiResponse.<Void>builder()
                .status(200)
                .success(true)
                .message("Password reset email sent successfully")
                .build();
    }

    @PutMapping("/change-password")
    public ApiResponse<User> changePassword(@RequestBody Map<String, String> requestBody) {
        String userId = requestBody.get("userId");
        String currentPassword = requestBody.get("currentPassword");
        String newPassword = requestBody.get("newPassword");
        User updatedUser = userService.changePassword(userId, currentPassword, newPassword);
        return ApiResponse.<User>builder()
                .status(200)
                .success(true)
                .data(updatedUser)
                .message("Password changed successfully")
                .build();
    }

    @PutMapping("/profile")
    public ApiResponse<User> updateProfile(@RequestBody User user) {
        String userId = user.getId();
        User updatedUser = userService.updateProfile(userId, user);
        return ApiResponse.<User>builder()
                .status(200)
                .success(true)
                .data(updatedUser)
                .message("Profile updated successfully")
                .build();
    }

    // NEW: Endpoint để tải lên avatar
    @PostMapping("/{id}/avatar")
    public ApiResponse<User> uploadAvatar(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        User updatedUser = userService.uploadAvatar(id, file);
        return ApiResponse.<User>builder()
                .status(200)
                .success(true)
                .data(updatedUser)
                .message("Avatar uploaded successfully")
                .build();
    }
}




