package com.java4.asm2polyoe.service.impl;

import com.java4.asm2polyoe.entity.User;
import com.java4.asm2polyoe.enums.ErrorCode;
import com.java4.asm2polyoe.exception.AppException;
import com.java4.asm2polyoe.mapper.UserMapper;
import com.java4.asm2polyoe.service.UseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UseService {

    private final UserMapper userMapper;

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
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION);
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
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION);
        }
    }

    @Override
    public User save(User user) {
        try {
            if (user == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            
            // Check if user already exists by email
            List<User> existingUsers = userMapper.findAll();
            boolean exists = existingUsers.stream()
                    .anyMatch(u -> u.getEmail() != null && u.getEmail().equals(user.getEmail()));
            
            if (exists) {
                throw new AppException(ErrorCode.USER_NOT_FOUND); // Using existing error code
            }
            
            userMapper.insert(user);
            return user;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION);
        }
    }

    @Override
    public User update(String id, User user) {
        try {
            if (id == null || user == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            
            // Check if user exists
            User existingUser = findById(id);
            if (existingUser == null) {
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }
            
            user.setId(id);
            userMapper.update(user);
            return user;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION);
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (id == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            
            // Check if user exists before deleting
            User existingUser = findById(id);
            if (existingUser == null) {
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }
            
            userMapper.delete(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION);
        }
    }
}

