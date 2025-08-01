package com.java4.asm2polyoe.service;

import com.java4.asm2polyoe.entity.Favorite;
import java.util.List;

public interface FavoriteService {
    List<Favorite> findAll();
    Favorite findById(Long id);
    Favorite findByUserIdAndVideoId(String userId, String videoId); // Thêm phương thức này
    Favorite save(Favorite favorite);
    Favorite update(Long id, Favorite favorite);
    void delete(Long id);
}

