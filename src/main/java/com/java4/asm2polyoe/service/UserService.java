package com.java4.asm2polyoe.service;

import com.java4.asm2polyoe.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(String id);
    User findByEmail(String email);
    User save(User user);
    User update(String id, User user);
    void delete(String id);

    // Authentication methods
    User login(String id, String password);
    User register(User user);
    void forgotPassword(String email);
    User changePassword(String userId, String currentPassword, String newPassword);
    User updateProfile(String userId, User updatedUser);

    // NEW: Method to upload avatar
    User uploadAvatar(String userId, MultipartFile file);
}



