package com.java4.asm2polyoe.controller;

import com.java4.asm2polyoe.dto.response.ApiResponse;
import com.java4.asm2polyoe.entity.Comment;
import com.java4.asm2polyoe.service.CommentService;
import com.java4.asm2polyoe.service.UserService; // Import UserService để lấy thông tin admin
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService; // Để kiểm tra quyền admin

    @GetMapping("/video/{videoId}")
    public ApiResponse<List<Comment>> getCommentsByVideoId(@PathVariable String videoId) {
        List<Comment> comments = commentService.getCommentsByVideoId(videoId);
        return ApiResponse.<List<Comment>>builder()
                .status(200)
                .success(true)
                .data(comments)
                .message("Fetched comments successfully")
                .build();
    }

    @PostMapping
    public ApiResponse<Comment> addComment(@RequestBody Comment comment) {
        Comment newComment = commentService.addComment(comment);
        return ApiResponse.<Comment>builder()
                .status(201)
                .success(true)
                .data(newComment)
                .message("Comment added successfully")
                .build();
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(@PathVariable Long commentId, @RequestParam String userId) {
        // Lấy thông tin người dùng để kiểm tra quyền admin
        Boolean isAdmin = false;
        try {
            isAdmin = userService.findById(userId).getAdmin();
        } catch (Exception e) {
            // Xử lý nếu không tìm thấy người dùng hoặc lỗi khác, mặc định không phải admin
            System.err.println("Error checking admin status for user " + userId + ": " + e.getMessage());
        }
        commentService.deleteComment(commentId, userId, isAdmin);
        return ApiResponse.<Void>builder()
                .status(204)
                .success(true)
                .message("Comment deleted successfully")
                .build();
    }
}

