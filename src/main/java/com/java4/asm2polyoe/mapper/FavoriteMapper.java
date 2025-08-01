package com.java4.asm2polyoe.mapper;

import com.java4.asm2polyoe.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param; // Import Param
import java.util.List;

@Mapper
public interface FavoriteMapper {
    List<Favorite> findAll();
    Favorite findById(@Param("id") Long id); // Thêm @Param
    Favorite findByUserIdAndVideoId(@Param("userId") String userId, @Param("videoId") String videoId); // Thêm phương thức này
    void insert(Favorite favorite);
    void update(Favorite favorite);
    void delete(@Param("id") Long id); // Thêm @Param
}


