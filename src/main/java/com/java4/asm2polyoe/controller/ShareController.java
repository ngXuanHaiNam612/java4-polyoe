package com.java4.asm2polyoe.controller;

import com.java4.asm2polyoe.dto.response.ApiResponse;
import com.java4.asm2polyoe.entity.Share;
import com.java4.asm2polyoe.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/shares")
@RequiredArgsConstructor
public class ShareController {

    private final ShareService shareService;

    @GetMapping
    public ApiResponse<List<Share>> getAllShares() {
        List<Share> shares = shareService.findAll();
        return ApiResponse.<List<Share>>builder()
                .status(200)
                .success(true)
                .data(shares)
                .message("Fetched all shares")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<Share> getShareById(@PathVariable Long id) {
        Share share = shareService.findById(id);
        return ApiResponse.<Share>builder()
                .status(200)
                .success(true)
                .data(share)
                .message("Share found")
                .build();
    }

    @PostMapping
    public ApiResponse<Share> createShare(@RequestBody Share share) {
        Share createdShare = shareService.save(share);
        return ApiResponse.<Share>builder()
                .status(201)
                .success(true)
                .data(createdShare)
                .message("Share created")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Share> updateShare(@PathVariable Long id, @RequestBody Share share) {
        Share updatedShare = shareService.update(id, share);
        return ApiResponse.<Share>builder()
                .status(200)
                .success(true)
                .data(updatedShare)
                .message("Share updated")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteShare(@PathVariable Long id) {
        shareService.delete(id);
        return ApiResponse.<Void>builder()
                .status(204)
                .success(true)
                .message("Share deleted")
                .build();
    }
}

