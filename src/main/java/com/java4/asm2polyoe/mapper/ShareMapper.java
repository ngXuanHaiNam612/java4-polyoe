package com.java4.asm2polyoe.mapper;

import com.java4.asm2polyoe.entity.Share;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ShareMapper {
    List<Share> findAll();
    Share findById(@Param("id") Long id);
    // Thêm phương thức mới để kiểm tra sự tồn tại của share theo userId, videoId và emails
    Share findByUserIdAndVideoIdAndEmails(@Param("userId") String userId, @Param("videoId") String videoId, @Param("emails") String emails);
    void insert(Share share);
    void update(Share share);
    void delete(@Param("id") Long id);
}

