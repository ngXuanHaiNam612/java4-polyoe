package com.java4.asm2polyoe.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_SQL(500, "Bad SQL! Check lại SQL"),
    UNCATCH_EXCEPTION(501, "Uncatch exception! không bắt được ngoại lệ"),
    NULL_POINTER_EXCEPTION(503, "Null Pointer! dữ liệu trả về trống"),

    LIST_USER_EMPTY(1000, "List user is empty!"),
    PASSWORD_INCORRECT(1001, "Password incorrect!"),
    USER_NOT_FOUND(1002, "User not found!"),

    // Video errors
    LIST_VIDEO_EMPTY(2000, "List video is empty!"),
    VIDEO_NOT_FOUND(2001, "Video not found!"),
    VIDEO_ALREADY_EXISTS(2002, "Video already exists!"),

    // Favorite errors
    LIST_FAVORITE_EMPTY(3000, "List favorite is empty!"),
    FAVORITE_NOT_FOUND(3001, "Favorite not found!"),
    FAVORITE_ALREADY_EXISTS(3002, "Favorite already exists!"),

    // Share errors
    LIST_SHARE_EMPTY(4000, "List share is empty!"),
    SHARE_NOT_FOUND(4001, "Share not found!"),
    SHARE_ALREADY_EXISTS(4002, "Share already exists!"),

    // ViewHistory errors
    LIST_VIEWHISTORY_EMPTY(5000, "List view history is empty!"),
    VIEWHISTORY_NOT_FOUND(5001, "View history not found!"),
    VIEWHISTORY_ALREADY_EXISTS(5002, "View history already exists!"),

    HTTP_STATUS_200(200, "ok"),
    HTTP_STATUS_400(400, "request error"),
    HTTP_STATUS_401(401, "no authentication"),
    HTTP_STATUS_403(403, "no authorities"),
    HTTP_STATUS_500(500, "server error");

    private final Integer status;
    private final String message;

}
