package com.freesia.imyourfreesia.handler;

import static com.freesia.imyourfreesia.handler.ValidationError.getParameterName;

import com.freesia.imyourfreesia.except.AccessTokenException;
import com.freesia.imyourfreesia.except.DuplicateEmailException;
import com.freesia.imyourfreesia.except.EmailSendingException;
import com.freesia.imyourfreesia.except.InvalidPasswordException;
import com.freesia.imyourfreesia.except.NotFoundException;
import com.freesia.imyourfreesia.except.UnexpectedValueException;
import com.freesia.imyourfreesia.except.UserNotActivatedException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidationError> errors = new ArrayList<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            log.error("name : {}, message : {}", fieldError.getField(), fieldError.getDefaultMessage());
            ValidationError exception = ValidationError.of(fieldError);
            errors.add(exception);
        }
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST, errors);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        List<ValidationError> errors = new ArrayList<>();
        for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
            String paramName = getParameterName(constraintViolation.getPropertyPath().toString());
            log.error("name : {}, message : {}", paramName, constraintViolation.getMessageTemplate());
            ValidationError exception = ValidationError.of(constraintViolation);
            errors.add(exception);
        }
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST, errors);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(AccessTokenException.class)
    protected ResponseEntity<ErrorResponse> handleAccessTokenException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_ACCESS_TOKEN);
        log.error("name : {}, message : {}", response.getError(), response.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    protected ResponseEntity<ErrorResponse> handleDuplicateEmailException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.DUPLICATE_EMAIL);
        log.error("name : {}, message : {}", response.getError(), response.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(EmailSendingException.class)
    protected ResponseEntity<ErrorResponse> handleEmailSendingException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.EMAIL_AUTH_CODE_NOT_SEND);
        log.error("name : {}, message : {}", response.getError(), response.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidPasswordException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_PASSWORD);
        log.error("name : {}, message : {}", response.getError(), response.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.DATA_NOT_FOUND);
        log.error("name : {}, message : {}", response.getError(), response.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(UserNotActivatedException.class)
    protected ResponseEntity<ErrorResponse> handleUserNotActivatedException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.USER_NOT_ACTIVATED);
        log.error("name : {}, message : {}", response.getError(), response.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(UnexpectedValueException.class)
    protected ResponseEntity<ErrorResponse> handleUnexpectedValueException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST);
        log.error("name : {}, message : {}", response.getError(), response.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.SERVER_ERROR);
        log.error("name : {}, message : {}", response.getError(), response.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}