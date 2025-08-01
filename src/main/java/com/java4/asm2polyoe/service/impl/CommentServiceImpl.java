package com.java4.asm2polyoe.service.impl;

import com.java4.asm2polyoe.entity.Comment;
import com.java4.asm2polyoe.entity.User;
import com.java4.asm2polyoe.enums.ErrorCode;
import com.java4.asm2polyoe.exception.AppException;
import com.java4.asm2polyoe.mapper.CommentMapper;
import com.java4.asm2polyoe.service.CommentService;
import com.java4.asm2polyoe.service.UserService; // Import UserService
import com.java4.asm2polyoe.service.VideoService; // Import VideoService
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final UserService userService; // Inject UserService
    private final VideoService videoService; // Inject VideoService

    @Override
    public List<Comment> getCommentsByVideoId(String videoId) {
        try {
            if (videoId == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            // Kiểm tra xem video có tồn tại không
            videoService.findById(videoId); // Sẽ ném AppException nếu không tìm thấy video

            List<Comment> comments = commentMapper.findAllByVideoId(videoId);
            if (comments.isEmpty()) {
                // Không ném lỗi nếu không có bình luận, chỉ trả về danh sách rỗng
                return List.of();
            }

            // Enrich comments with user data
            return comments.stream().map(comment -> {
                User user = null;
                try {
                    user = userService.findById(comment.getUserId());
                } catch (AppException e) {
                    // Log lỗi nếu không tìm thấy người dùng nhưng không dừng luồng
                    System.err.println("User not found for comment: " + comment.getUserId());
                }
                comment.setUser(user); // Set the user object
                return comment;
            }).collect(Collectors.toList());

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public Comment addComment(Comment comment) {
        try {
            if (comment == null || comment.getVideoId() == null || comment.getUserId() == null || comment.getContent() == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            if (comment.getContent().trim().isEmpty()) {
                throw new AppException(ErrorCode.COMMENT_CONTENT_EMPTY);
            }

            // Kiểm tra xem video và người dùng có tồn tại không
            videoService.findById(comment.getVideoId());
            userService.findById(comment.getUserId());

            comment.setCommentDate(new Date()); // Set current date
            commentMapper.insert(comment);
            return comment;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public void deleteComment(Long commentId, String currentUserId, Boolean isAdmin) {
        try {
            if (commentId == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            Comment existingComment = commentMapper.findById(commentId);
            if (existingComment == null) {
                throw new AppException(ErrorCode.COMMENT_NOT_FOUND);
            }

            // Chỉ cho phép xóa nếu là chủ sở hữu bình luận hoặc là admin
            if (!existingComment.getUserId().equals(currentUserId) && !isAdmin) {
                throw new AppException(ErrorCode.COMMENT_NOT_AUTHORIZED);
            }

            commentMapper.delete(commentId);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }
}

