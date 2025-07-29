package com.java4.asm2polyoe.service;

import com.java4.asm2polyoe.entity.User;
import java.util.List;

public interface UseService {
    List<User> findAll();
    User findById(String id);
    User save(User user);
    User update(String id, User user);
    void delete(String id);
}
