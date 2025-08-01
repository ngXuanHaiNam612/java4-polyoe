package com.java4.asm2polyoe.exception;

import com.java4.asm2polyoe.dto.response.ApiResponse;
import com.java4.asm2polyoe.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandling.class);

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException e) {
        // Log chi tiết lỗi
        logger.error("AppException occurred: {} - {}", e.getErrorCode().getStatus(), e.getErrorCode().getMessage(), e);

        ApiResponse<?> response = ApiResponse.builder()
                .status(e.getErrorCode().getStatus())
                .success(false)
                .message(e.getErrorCode().getMessage()) // Sử dụng thông điệp từ ErrorCode
                .build();

        // Trả về HttpStatus tương ứng với ErrorCode.status
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // Mặc định
        try {
            httpStatus = HttpStatus.valueOf(e.getErrorCode().getStatus());
        } catch (IllegalArgumentException ex) {
            // Nếu status không phải là HTTP status code hợp lệ, giữ nguyên INTERNAL_SERVER_ERROR
            logger.warn("Invalid HTTP status code in ErrorCode: {}", e.getErrorCode().getStatus());
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    // Bạn có thể có các ExceptionHandler khác ở đây
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception e) {
        logger.error("An unexpected error occurred: {}", e.getMessage(), e);
        ApiResponse<?> response = ApiResponse.builder()
                .status(ErrorCode.UNCATCH_EXCEPTION.getStatus())
                .success(false)
                .message(ErrorCode.UNCATCH_EXCEPTION.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}



