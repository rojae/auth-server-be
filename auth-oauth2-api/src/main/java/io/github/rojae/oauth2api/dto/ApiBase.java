package io.github.rojae.oauth2api.dto;

import io.github.rojae.oauth2api.common.enums.ApiCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ApiBase<T> {
    private final String code;
    private final String reason;
    private final String requestTime;
    private final String requestId;
    private final T data;

    public ApiBase(ApiCode apiCode) {
        this.code = apiCode.getCode();
        this.reason = apiCode.getReason();
        this.data = null;
        this.requestTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.requestId = UUID.randomUUID().toString();
    }

    public ApiBase(ApiCode apiCode, T data) {
        this.code = apiCode.getCode();
        this.reason = apiCode.getReason();
        this.data = data;
        this.requestTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.requestId = UUID.randomUUID().toString();
    }

    public ApiBase(ApiCode apiCode, String reason) {
        this.code = apiCode.getCode();
        this.reason = String.format("%s, (%s)", apiCode.getReason(), reason);
        this.data = null;
        this.requestTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.requestId = UUID.randomUUID().toString();
    }

    public ApiBase(ApiCode apiCode, String reason, T data) {
        this.code = apiCode.getCode();
        this.reason = reason;
        this.data = data;
        this.requestTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.requestId = UUID.randomUUID().toString();
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public T getData() {
        return data;
    }
}
