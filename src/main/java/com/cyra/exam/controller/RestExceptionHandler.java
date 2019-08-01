package com.cyra.exam.controller;

import com.cyra.exam.exception.RangeDateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String error = MessageFormat.format("{0} should be in yyyyMMdd format", ex.getName());
        return buildResponseEntity(new ApiResponse(HttpStatus.BAD_REQUEST, "error", error, ex));
    }

    @ExceptionHandler({RangeDateException.class})
    public ResponseEntity<Object> handleRangeDateException(RangeDateException ex) {
        String error = ex.getMessage();
        return buildResponseEntity(new ApiResponse(HttpStatus.OK, "error", error));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiResponse apiResponse) {
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }
}
