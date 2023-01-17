package com.visual.open.anpr.server.controller.base;

import com.visual.open.anpr.server.domain.common.ResponseInfo;
import com.visual.open.anpr.server.utils.ResponseBuilder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseInfo<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseBuilder.error(ex.getBindingResult().getFieldError().getDefaultMessage());
    }

}
