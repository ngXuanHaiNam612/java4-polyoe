package com.java4.asm2polyoe.service.impl;

import com.java4.asm2polyoe.entity.User;
import com.java4.asm2polyoe.enums.ErrorCode;
import com.java4.asm2polyoe.exception.AppException;
import com.java4.asm2polyoe.mapper.UserMapper;
import com.java4.asm2polyoe.service.UserService;
import com.java4.asm2polyoe.service.EmailService; // Import EmailService
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID; // Import UUID để tạo mật khẩu ngẫu nhiên

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final EmailService emailService; // Inject EmailService

    @Override
    public List<User> findAll() {
        try {
            List<User> users = userMapper.findAll();
            if (users.isEmpty()) {
                throw new AppException(ErrorCode.LIST_USER_EMPTY);
            }
            return users;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public User findById(String id) {
        try {
            if (id == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            User user = userMapper.findById(id);
            if (user == null) {
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }
            return user;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            if (email == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            User user = userMapper.findByEmail(email);
            if (user == null) {
                throw new AppException(ErrorCode.EMAIL_NOT_FOUND);
            }
            return user;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public User save(User user) {
        try {
            if (user == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            // Kiểm tra xem ID đã tồn tại chưa
            if (userMapper.findById(user.getId()) != null) {
                throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
            }
            // Kiểm tra xem email đã tồn tại chưa
            if (userMapper.findByEmail(user.getEmail()) != null) {
                throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
            }
            userMapper.insert(user);
            return user;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public User update(String id, User user) {
        try {
            if (id == null || user == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            User existingUser = findById(id);
            if (existingUser == null) {
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }

            // Kiểm tra email trùng lặp nếu email được thay đổi
            if (!Objects.equals(existingUser.getEmail(), user.getEmail())) {
                User userWithNewEmail = userMapper.findByEmail(user.getEmail());
                if (userWithNewEmail != null && !userWithNewEmail.getId().equals(id)) {
                    throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
                }
            }

            existingUser.setFullname(user.getFullname());
            existingUser.setEmail(user.getEmail());
            existingUser.setAdmin(user.getAdmin());

            userMapper.update(existingUser);
            return existingUser;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (id == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            User existingUser = findById(id);
            if (existingUser == null) {
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }
            userMapper.delete(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    // --- Các phương thức xác thực ---
    @Override
    public User login(String id, String password) {
        try {
            User user = userMapper.findById(id);
            if (user == null) {
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }
            if (!user.getPassword().equals(password)) {
                throw new AppException(ErrorCode.INVALID_CREDENTIALS);
            }
            user.setToken("dummy_jwt_token_for_" + user.getId());
            return user;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public User register(User user) {
        try {
            if (userMapper.findById(user.getId()) != null) {
                throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
            }
            if (userMapper.findByEmail(user.getEmail()) != null) {
                throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
            }
            user.setAdmin(false);
            userMapper.insert(user);
            return user;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public void forgotPassword(String email) {
        try {
            User user = userMapper.findByEmail(email);
            if (user == null) {
                throw new AppException(ErrorCode.EMAIL_NOT_FOUND); // Sử dụng EMAIL_NOT_FOUND
            }

            // 1. Tạo mật khẩu tạm thời ngẫu nhiên
            String newTempPassword = UUID.randomUUID().toString().substring(0, 8); // Mật khẩu 8 ký tự ngẫu nhiên

            // 2. Cập nhật mật khẩu mới vào cơ sở dữ liệu
            user.setPassword(newTempPassword);
            userMapper.update(user); // Cập nhật người dùng với mật khẩu mới

            // 3. Gửi email chứa mật khẩu tạm thời
            emailService.sendPasswordResetEmail(user.getEmail(), user.getFullname() != null ? user.getFullname() : user.getId(), newTempPassword);

            System.out.println("Password reset email sent to: " + email + " with new temporary password.");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public User changePassword(String userId, String currentPassword, String newPassword) {
        try {
            User user = userMapper.findById(userId);
            if (user == null) {
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }
            if (!user.getPassword().equals(currentPassword)) {
                throw new AppException(ErrorCode.CURRENT_PASSWORD_INCORRECT);
            }
            user.setPassword(newPassword);
            userMapper.update(user);
            return user;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public User updateProfile(String userId, User updatedUser) {
        try {
            User existingUser = userMapper.findById(userId);
            if (existingUser == null) {
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }
            existingUser.setFullname(updatedUser.getFullname());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setBio(updatedUser.getBio());
            if (!Objects.equals(existingUser.getEmail(), updatedUser.getEmail())) {
                if (userMapper.findByEmail(updatedUser.getEmail()) != null) {
                    throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
                }
            }
            userMapper.update(existingUser);
            return existingUser;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }
}





