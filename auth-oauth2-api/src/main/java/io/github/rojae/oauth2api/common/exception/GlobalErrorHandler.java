package io.github.rojae.oauth2api.common.exception;

import io.github.rojae.oauth2api.common.enums.ApiCode;
import io.github.rojae.oauth2api.dto.ApiBase;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackAttachment;
import net.gpedro.integrations.slack.SlackMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalErrorHandler extends GlobalErrorNotification{

    @ExceptionHandler({Exception.class})
    public void exceptionHandler(Exception e){
        e.printStackTrace();
        this.notification(e);
    }

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



}