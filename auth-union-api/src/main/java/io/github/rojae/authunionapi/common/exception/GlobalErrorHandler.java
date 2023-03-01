package io.github.rojae.authunionapi.common.exception;

import io.github.rojae.authunionapi.common.enums.ApiCode;
import io.github.rojae.authunionapi.dto.ApiBase;
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
import org.springframework.web.bind.support.WebExchangeBindException;

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

    @ExceptionHandler({WebExchangeBindException.class})
    public ResponseEntity<ApiBase<Object>> handleResponseBodyError(WebExchangeBindException ex) {
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

    // Core API Exception //
    @ExceptionHandler(CoreApiException.class)
    public ResponseEntity<ApiBase<Object>> coreApiException(CoreApiException e) {
        this.notification(e);
        return ResponseEntity.badRequest()
                .body(new ApiBase<>(ApiCode.COREAPI_ERROR, String.format("[%s] %s", e.getUrl(), e.getMessage())));
    }

    // OAuth2 API Exception //
    @ExceptionHandler(OAuth2ApiException.class)
    public ResponseEntity<ApiBase<Object>> oauth2ApiException(OAuth2ApiException e){
        this.notification(e);
        return ResponseEntity.badRequest()
                .body(new ApiBase<>(ApiCode.OAUTH2API_ERROR, String.format("[%s] %s", e.getUrl(), e.getMessage())));
    }

    // Social API Exception //
    @ExceptionHandler(SocialApiException.class)
    public ResponseEntity<ApiBase<Object>> socialApiException(SocialApiException e) {
        this.notification(e);
        return ResponseEntity.badRequest()
                .body(new ApiBase<>(ApiCode.SOCIALAPI_ERROR, String.format("[%s] %s", e.getUrl(), e.getMessage())));
    }

    // SMTP API Exception //
    @ExceptionHandler(SmtpApiException.class)
    public ResponseEntity<ApiBase<Object>> smtpApiException(SocialApiException e) {
        this.notification(e);
        return ResponseEntity.badRequest()
                .body(new ApiBase<>(ApiCode.SMTPAPI_ERROR, String.format("[%s] %s", e.getUrl(), e.getMessage())));
    }

}