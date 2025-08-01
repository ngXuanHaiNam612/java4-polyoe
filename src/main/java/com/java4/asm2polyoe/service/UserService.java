package com.java4.asm2polyoe.service;

import com.java4.asm2polyoe.entity.User; // Đảm bảo import đúng entity.User
import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(String id);

    User findByEmail(String email);

    User save(User user); // Dùng cho admin tạo user mới
    User update(String id, User user);
    void delete(String id);

    // Các phương thức xác thực mới
    User login(String id, String password);
    User register(User user);
    void forgotPassword(String email);
    User changePassword(String userId, String currentPassword, String newPassword);
    User updateProfile(String userId, User user);
}

