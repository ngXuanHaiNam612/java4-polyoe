package com.java4.asm2polyoe.service.impl;

import com.java4.asm2polyoe.entity.Favorite;
import com.java4.asm2polyoe.enums.ErrorCode;
import com.java4.asm2polyoe.exception.AppException;
import com.java4.asm2polyoe.mapper.FavoriteMapper;
import com.java4.asm2polyoe.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;

    @Override
    public List<Favorite> findAll() {
        try {
            List<Favorite> favorites = favoriteMapper.findAll();
            if (favorites.isEmpty()) {
                throw new AppException(ErrorCode.LIST_FAVORITE_EMPTY);
            }
            return favorites;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public Favorite findById(Long id) {
        try {
            if (id == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            Favorite favorite = favoriteMapper.findById(id);
            if (favorite == null) {
                throw new AppException(ErrorCode.FAVORITE_NOT_FOUND);
            }
            return favorite;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public Favorite findByUserIdAndVideoId(String userId, String videoId) {
        try {
            if (userId == null || videoId == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            return favoriteMapper.findByUserIdAndVideoId(userId, videoId);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public Favorite save(Favorite favorite) {
        try {
            if (favorite == null || favorite.getUserId() == null || favorite.getVideoId() == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }

            if (favorite.getLikeDate() == null) {
                favorite.setLikeDate(new Date());
            }

            // Kiểm tra xem favorite đã tồn tại chưa bằng phương thức mới
            Favorite existingFavorite = findByUserIdAndVideoId(favorite.getUserId(), favorite.getVideoId());
            if (existingFavorite != null) {
                throw new AppException(ErrorCode.FAVORITE_ALREADY_EXISTS);
            }

            favoriteMapper.insert(favorite);
            return favorite; // favorite object now contains the generated ID
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public Favorite update(Long id, Favorite favorite) {
        try {
            if (id == null || favorite == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            Favorite existingFavorite = findById(id);
            if (existingFavorite == null) {
                throw new AppException(ErrorCode.FAVORITE_NOT_FOUND);
            }

            favorite.setId(id);
            favoriteMapper.update(favorite);
            return favorite;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (id == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            Favorite existingFavorite = findById(id);
            if (existingFavorite == null) {
                throw new AppException(ErrorCode.FAVORITE_NOT_FOUND);
            }
            favoriteMapper.delete(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }
}


