package com.java4.asm2polyoe.service;

import com.java4.asm2polyoe.entity.Share;
import java.util.List;

public interface ShareService {
    List<Share> findAll();
    Share findById(Long id);
    Share save(Share share);
    Share update(Long id, Share share);
    void delete(Long id);
    Share findByUserIdAndVideoIdAndEmails(String userId, String videoId, String emails); // Thêm phương thức này
}


