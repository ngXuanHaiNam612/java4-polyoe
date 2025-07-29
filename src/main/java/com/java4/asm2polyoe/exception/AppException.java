package com.java4.asm2polyoe.exception;


import com.java4.asm2polyoe.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppException extends RuntimeException {
    private ErrorCode errorCode;
}
