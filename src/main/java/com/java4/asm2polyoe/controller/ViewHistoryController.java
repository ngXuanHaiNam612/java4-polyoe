package com.java4.asm2polyoe.controller;

import com.java4.asm2polyoe.dto.response.ApiResponse;
import com.java4.asm2polyoe.entity.ViewHistory;
import com.java4.asm2polyoe.service.ViewHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/viewhistories")
@RequiredArgsConstructor
public class ViewHistoryController {

    private final ViewHistoryService viewHistoryService;

    @GetMapping
    public ApiResponse<List<ViewHistory>> getAllViewHistories() {
        List<ViewHistory> viewHistories = viewHistoryService.findAll();
        return ApiResponse.<List<ViewHistory>>builder()
                .status(200)
                .success(true)
                .data(viewHistories)
                .message("Fetched all view histories")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ViewHistory> getViewHistoryById(@PathVariable Long id) {
        ViewHistory viewHistory = viewHistoryService.findById(id);
        return ApiResponse.<ViewHistory>builder()
                .status(200)
                .success(true)
                .data(viewHistory)
                .message("View history found")
                .build();
    }

    @PostMapping
    public ApiResponse<ViewHistory> createViewHistory(@RequestBody ViewHistory viewHistory) {
        ViewHistory createdViewHistory = viewHistoryService.save(viewHistory);
        return ApiResponse.<ViewHistory>builder()
                .status(201)
                .success(true)
                .data(createdViewHistory)
                .message("View history created")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ViewHistory> updateViewHistory(@PathVariable Long id, @RequestBody ViewHistory viewHistory) {
        ViewHistory updatedViewHistory = viewHistoryService.update(id, viewHistory);
        return ApiResponse.<ViewHistory>builder()
                .status(200)
                .success(true)
                .data(updatedViewHistory)
                .message("View history updated")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteViewHistory(@PathVariable Long id) {
        viewHistoryService.delete(id);
        return ApiResponse.<Void>builder()
                .status(204)
                .success(true)
                .message("View history deleted")
                .build();
    }
}

