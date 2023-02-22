package com.researchecosystems.todoapp.controller;

import com.researchecosystems.todoapp.exception.BusinessException;
import com.researchecosystems.todoapp.exception.ErrorCode;
import com.researchecosystems.todoapp.model.response.ErrorModel;
import com.researchecosystems.todoapp.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorModel> customHandleBusinessException(BusinessException ex, WebRequest request) {
        LOGGER.info("Business Error: {}", ex.getMessage());
        ErrorModel error = ErrorModel.builder()
                .timestamp(DateUtil.now())
                .statusCode(ex.getStatusCode())
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.resolve(ex.getStatusCode()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorModel error = ErrorModel.builder()
                .timestamp(DateUtil.now())
                .statusCode(ErrorCode.validation.getHttpCode())
                .errorCode(ErrorCode.validation.name())
                .message(String.join(", ", errors))
                .build();
        return new ResponseEntity<>(error, headers, HttpStatus.resolve(ErrorCode.validation.getHttpCode()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorModel> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ErrorModel error = ErrorModel.builder()
                .timestamp(DateUtil.now())
                .statusCode(ErrorCode.validation.getHttpCode())
                .errorCode(ErrorCode.validation.name())
                .message(String.join(", ", errors))
                .build();
        return new ResponseEntity<>(error, HttpStatus.resolve(ErrorCode.validation.getHttpCode()));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {
        ErrorModel error = ErrorModel.builder()
                .timestamp(DateUtil.now())
                .statusCode(status.value())
                .errorCode(ErrorCode.unknown.name())
                .message(ex.getRequestPartName() + " eksik!")
                .build();
        return new ResponseEntity<>(error, headers, status);
    }
}
