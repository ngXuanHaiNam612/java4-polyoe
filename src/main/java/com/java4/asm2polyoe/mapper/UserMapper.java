package com.java4.asm2polyoe.mapper;

import com.java4.asm2polyoe.entity.User; // Đảm bảo import đúng entity.User
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param; // Import Param
import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
    User findById(@Param("id") String id);
    User findByEmail(@Param("email") String email); // Thêm phương thức này
    void insert(User user);
    void update(User user);
    void delete(@Param("id") String id); // Thêm @Param cho delete
}

