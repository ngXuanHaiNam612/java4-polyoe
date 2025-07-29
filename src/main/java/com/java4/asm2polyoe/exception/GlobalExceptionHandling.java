package com.java4.asm2polyoe.exception;

import com.java4.asm2polyoe.dto.response.ApiResponse;
import com.java4.asm2polyoe.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandling {

    private void logError(Exception e, HttpServletRequest request) {
        log.error("Fail to call API" + request.getRequestURI() + " : " +e);
    }

    // Bad SQL Grammar
    @ExceptionHandler(value = BadSqlGrammarException.class)
    ResponseEntity<ApiResponse> handleBadSQLGrammarRequestException(BadSqlGrammarException e, HttpServletRequest request) {

       logError(e, request);

        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                .status(ErrorCode.BAD_SQL.getStatus())
                .success(false)
                .message(ErrorCode.BAD_SQL.getMessage())
                .build());
    }

    //Null pointer
    @ExceptionHandler(value = NullPointerException.class)
    ResponseEntity<ApiResponse> handleNullPointerException(NullPointerException e, HttpServletRequest request) {

        logError(e, request);

        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .status(ErrorCode.NULL_POINTER_EXCEPTION.getStatus())
                        .success(false)
                        .message(ErrorCode.NULL_POINTER_EXCEPTION.getMessage())
                        .build());
    }

    //App Excoption
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException e, HttpServletRequest request) {

        logError(e, request);
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .status(errorCode.getStatus())
                        .success(false)
                        .message(errorCode.getMessage())
                        .build());
    }

    //Unknown error
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handleUncatchException(Exception e, HttpServletRequest request) {

        logError(e, request);

        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .status(ErrorCode.UNCATCH_EXCEPTION.getStatus())
                        .success(false)
                        .message(e.getClass().getSimpleName())
                        .build());
    }
}
