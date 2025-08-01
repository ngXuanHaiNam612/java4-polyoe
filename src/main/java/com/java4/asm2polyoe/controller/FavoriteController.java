package com.java4.asm2polyoe.controller;

import com.java4.asm2polyoe.dto.response.ApiResponse;
import com.java4.asm2polyoe.entity.Favorite;
import com.java4.asm2polyoe.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping
    public ApiResponse<List<Favorite>> getAllFavorites() {
        List<Favorite> favorites = favoriteService.findAll();
        return ApiResponse.<List<Favorite>>builder()
                .status(200)
                .success(true)
                .data(favorites)
                .message("Fetched all favorites")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<Favorite> getFavoriteById(@PathVariable Long id) {
        Favorite favorite = favoriteService.findById(id);
        return ApiResponse.<Favorite>builder()
                .status(200)
                .success(true)
                .data(favorite)
                .message("Favorite found")
                .build();
    }

    @PostMapping
    public ApiResponse<Favorite> createFavorite(@RequestBody Favorite favorite) {
        Favorite createdFavorite = favoriteService.save(favorite);
        return ApiResponse.<Favorite>builder()
                .status(201)
                .success(true)
                .data(createdFavorite)
                .message("Favorite created")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Favorite> updateFavorite(@PathVariable Long id, @RequestBody Favorite favorite) {
        Favorite updatedFavorite = favoriteService.update(id, favorite);
        return ApiResponse.<Favorite>builder()
                .status(200)
                .success(true)
                .data(updatedFavorite)
                .message("Favorite updated")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFavorite(@PathVariable Long id) {
        favoriteService.delete(id);
        return ApiResponse.<Void>builder()
                .status(204)
                .success(true)
                .message("Favorite deleted")
                .build();
    }
}

