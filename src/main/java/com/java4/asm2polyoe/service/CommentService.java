package com.java4.asm2polyoe.service;

import com.java4.asm2polyoe.entity.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByVideoId(String videoId);
    Comment addComment(Comment comment);
    void deleteComment(Long commentId, String currentUserId, Boolean isAdmin);
}

