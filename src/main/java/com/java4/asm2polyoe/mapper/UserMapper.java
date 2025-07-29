package com.java4.asm2polyoe.mapper;

import com.java4.asm2polyoe.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
    User findById(String id);
    void insert(User user);
    void update(User user);
    void delete(String id);
}
