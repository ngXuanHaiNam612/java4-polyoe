package com.java4.asm2polyoe.service.impl;

import com.java4.asm2polyoe.entity.User;
import com.java4.asm2polyoe.enums.ErrorCode;
import com.java4.asm2polyoe.exception.AppException;
import com.java4.asm2polyoe.mapper.UserMapper;
import com.java4.asm2polyoe.service.UserService;
import com.java4.asm2polyoe.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final EmailService emailService;

    // Thư mục lưu trữ avatar. Đảm bảo thư mục này tồn tại trong src/main/resources/static
    private final String UPLOAD_DIR = "src/main/resources/static/avatars/";

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
            // REMOVED: Không set default avatar ở backend khi save
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
            existingUser.setBio(user.getBio());
            existingUser.setAvatarUrl(user.getAvatarUrl()); // Cập nhật avatarUrl khi update profile

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
            // REMOVED: Không set default avatar ở backend khi register
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
                throw new AppException(ErrorCode.EMAIL_NOT_FOUND);
            }

            String newTempPassword = UUID.randomUUID().toString().substring(0, 8);

            user.setPassword(newTempPassword);
            userMapper.update(user);

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
            existingUser.setAvatarUrl(updatedUser.getAvatarUrl()); // Cập nhật avatarUrl khi update profile

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

    @Override
    public User uploadAvatar(String userId, MultipartFile file) {
        try {
            User user = findById(userId);
            if (user == null) {
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }

            if (file.isEmpty()) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }

            // Tạo thư mục nếu chưa tồn tại
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Lấy phần mở rộng của file
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // Tạo tên file duy nhất
            String fileName = userId + "_" + UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadPath.resolve(fileName);

            // Lưu file
            Files.copy(file.getInputStream(), filePath);

            // Cập nhật avatarUrl trong database
            String avatarUrl = "/avatars/" + fileName; // Đường dẫn tương đối để frontend có thể truy cập
            user.setAvatarUrl(avatarUrl);
            userMapper.update(user); // Cập nhật người dùng với avatar mới

            return user;
        } catch (AppException e) {
            throw e;
        } catch (IOException e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, new Exception("Failed to store file: " + e.getMessage(), e));
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }
}








