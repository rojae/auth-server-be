package io.github.rojae.authcoreapi.common.exception;

import io.github.rojae.authcoreapi.common.enums.ApiCode;
import io.github.rojae.authcoreapi.dto.ApiBase;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalErrorHandler {

    // Validation Body
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiBase<Object>> handleResponseBodyError(MethodArgumentNotValidException ex) {
        var error = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .sorted()
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest()
                .body(new ApiBase<>(ApiCode.INVALID_BODY, error));
    }

    // Validation QueryString
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<ApiBase<Object>> handleMissingServletRequestParameterException(Throwable e) {
        return ResponseEntity.badRequest()
                .body(new ApiBase<>(ApiCode.INVALID_QUERYSTRING, e.getMessage()));
    }

    // Missing Header
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiBase<Object>> handleMissingRequestHeaderException(Throwable e) {
        return ResponseEntity.badRequest()
                .body(new ApiBase<>(ApiCode.INVALID_HEADER, e.getMessage()));
    }

    // Validation Data (QueryString, Header)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiBase<Object>> handleConstraintViolationException(Throwable e) {
        return ResponseEntity.badRequest()
                .body(new ApiBase<>(ApiCode.INVALID_QUERYSTRING_HEADER, e.getMessage()));
    }


    ///////////////////////////////////////////
    ////////////////// Signup //////////////////
    ///////////////////////////////////////////

    // Login
    @ExceptionHandler(SignupAccountException.class)
    public ResponseEntity<ApiBase<Object>> handleSignupAccountException(Throwable e) {
        return ResponseEntity.ok()
                .body(new ApiBase<>(ApiCode.SIGNUP_FAILED, e.getMessage()));
    }

    ///////////////////////////////////////////
    ////////////////// LOGIN //////////////////
    ///////////////////////////////////////////

    // Login
    @ExceptionHandler(LoginAccountInvalidException.class)
    public ResponseEntity<ApiBase<Object>> handleLoginAccountInvalidException(Throwable e) {
        return ResponseEntity.ok()
                .body(new ApiBase<>(ApiCode.LOGIN_ACCOUNT_INVALID, e.getMessage()));
    }


}