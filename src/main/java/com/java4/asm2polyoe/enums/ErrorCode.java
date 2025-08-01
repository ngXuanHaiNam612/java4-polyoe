package com.java4.asm2polyoe.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATCH_EXCEPTION(9999, "Uncatch exception! không bắt được ngoại lệ", HttpStatus.INTERNAL_SERVER_ERROR),
    NULL_POINTER_EXCEPTION(1000, "Null pointer exception!", HttpStatus.INTERNAL_SERVER_ERROR),

    // User related errors
    USER_NOT_FOUND(2000, "User not found!", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(2001, "User already exists!", HttpStatus.CONFLICT),
    INVALID_CREDENTIALS(2002, "Invalid username or password!", HttpStatus.UNAUTHORIZED),
    PASSWORD_MISMATCH(2003, "Current password does not match!", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(2004, "Email not found!", HttpStatus.NOT_FOUND),
    LIST_USER_EMPTY(2005, "List user is empty!", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS(2006, "Email already exists!", HttpStatus.CONFLICT),
    CURRENT_PASSWORD_INCORRECT(2007, "Current password is incorrect!", HttpStatus.BAD_REQUEST),

    // Video related errors
    VIDEO_NOT_FOUND(3000, "Video not found!", HttpStatus.NOT_FOUND),
    LIST_VIDEO_EMPTY(3001, "List video is empty!", HttpStatus.NOT_FOUND),
    VIDEO_ALREADY_EXISTS(3002, "Video with this ID already exists!", HttpStatus.CONFLICT),

    // Favorite related errors
    FAVORITE_NOT_FOUND(4000, "Favorite not found!", HttpStatus.NOT_FOUND),
    FAVORITE_ALREADY_EXISTS(4001, "Favorite already exists for this user and video!", HttpStatus.CONFLICT),
    LIST_FAVORITE_EMPTY(4002, "List favorite is empty!", HttpStatus.NOT_FOUND),

    // Share related errors
    SHARE_NOT_FOUND(5000, "Share not found!", HttpStatus.NOT_FOUND),
    SHARE_ALREADY_EXISTS(5001, "Share already exists for this user, video, and email!", HttpStatus.CONFLICT),
    LIST_SHARE_EMPTY(5002, "List share is empty!", HttpStatus.NOT_FOUND),

    // View History related errors
    VIEWHISTORY_NOT_FOUND(7000, "View history not found!", HttpStatus.NOT_FOUND),
    VIEWHISTORY_ALREADY_EXISTS(7001, "View history already exists for this user and video!", HttpStatus.CONFLICT),
    LIST_VIEWHISTORY_EMPTY(7002, "List view history is empty!", HttpStatus.NOT_FOUND),

    // Report related errors
    NO_REPORT_DATA_FOUND(6000, "No report data found!", HttpStatus.NOT_FOUND),

    // Comment related errors (NEW)
    COMMENT_NOT_FOUND(8000, "Comment not found!", HttpStatus.NOT_FOUND),
    LIST_COMMENT_EMPTY(8001, "List comment is empty!", HttpStatus.NOT_FOUND),
    COMMENT_CONTENT_EMPTY(8002, "Comment content cannot be empty!", HttpStatus.BAD_REQUEST),
    COMMENT_NOT_AUTHORIZED(8003, "You are not authorized to delete this comment!", HttpStatus.FORBIDDEN);


    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getStatus() {
        return httpStatus.value();
    }
}




