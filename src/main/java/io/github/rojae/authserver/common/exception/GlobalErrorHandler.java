package io.github.rojae.authserver.common.exception;

import io.github.rojae.authserver.common.enums.ApiCode;
import io.github.rojae.authserver.dto.ApiBase;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    // Redis Token Server's Validation
    @ExceptionHandler({InvalidTokenException.class, InvalidKakaoTokenException.class, DatabaseTransactionException.class, SignupDuplicateException.class, LoginAccountInvalidException.class, AuthAccountInvalidException.class})
    public ResponseEntity<ApiBase<Object>> handleInvalidTokenException(Throwable e){
        if(e instanceof InvalidTokenException)
            return ResponseEntity.badRequest().body(new ApiBase<>(ApiCode.INVALID_SERVICE_TOKEN, e.getMessage()));
        else if(e instanceof InvalidKakaoTokenException)
            return ResponseEntity.badRequest().body(new ApiBase<>(ApiCode.INVALID_KAKAO_TOKEN, e.getMessage()));
        else if(e instanceof DatabaseTransactionException)
            return ResponseEntity.badRequest().body(new ApiBase<>(ApiCode.DATABASE_TRANSACTION_FAIL, e.getMessage()));
        else if(e instanceof SignupDuplicateException)
            return ResponseEntity.badRequest().body(new ApiBase<>(ApiCode.SIGNUP_DUPLICATE, e.getMessage()));
        else if(e instanceof LoginAccountInvalidException)
            return ResponseEntity.badRequest().body(new ApiBase<>(ApiCode.LOGIN_ACCOUNT_INVALID, e.getMessage()));
        else if(e instanceof AuthAccountInvalidException)
            return ResponseEntity.badRequest().body(new ApiBase<>(ApiCode.AUTH_ACCOUNT_INVALID, e.getMessage()));


        return ResponseEntity.badRequest().body(new ApiBase<>(ApiCode.INVALID_SERVICE_TOKEN, e.getMessage()));
    }


}