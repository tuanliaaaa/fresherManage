package com.g11.FresherManage.exception;

import com.g11.FresherManage.dto.ResponseError;
import com.g11.FresherManage.exception.base.AccessDeniedException;
import com.g11.FresherManage.exception.base.NotFoundException;
import com.g11.FresherManage.exception.token.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AdviceController {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseError<?>> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(ResponseError.of(401,ex.getMessage(),ex.getParams(),ex.getCode()),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)

    public ResponseEntity<ResponseError<String>> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        return new ResponseEntity<>(ResponseError.of(403,"Access Denied","Token hết hạn","com.vmo.def"),HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseError<String>> handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ResponseError.of(404,"Not Found",ex.getMessage(), ex.getCode()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error(ex.getMessage());
        ResponseError response = ResponseError.of(
                404,
                "Not Found",
                ex.getMessage(),
                ex.getCode());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleInvalidTokenException(InvalidTokenException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ResponseError.of(401,"UNAUTHORIZED",ex.getMessage(), ex.getCode()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError<Map<String ,String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(ResponseError.of(400,"validate error",errors,"com.cmo.validate"),HttpStatus.BAD_REQUEST);
    }

}