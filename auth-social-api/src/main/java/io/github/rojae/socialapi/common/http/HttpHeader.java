package io.github.rojae.socialapi.common.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    private MediaType mediaType;
    private Map<String, String> keyValue;

    public HttpHeader() {
    }

    public HttpHeader(MediaType mediaType){
        this.mediaType = mediaType;
        this.keyValue = new HashMap<String, String>();
    }

    public HttpHeader(MediaType mediaType, Map<String, String> keyValue) {
        this.mediaType = mediaType;
        this.keyValue = keyValue;
    }

    public void addHeader(String key, String value){
        this.keyValue.put(key, value);
    }

    public void addHeader(Map<String, String> newKeyValue){
        newKeyValue.forEach(
                (key, value) -> this.keyValue.merge(key, value, (v1, v2) -> v2)
        );
    }

    public HttpHeaders build(){
        HttpHeaders build = new HttpHeaders();
        this.keyValue.forEach(build::add);
        return build;
    }

}