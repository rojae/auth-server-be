package io.github.rojae.authserver.common.http;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.logging.Logger;

@Component
public class RestProvider {
    private final RestTemplate restTemplate;
    Logger logger = Logger.getLogger(RestProvider.class.getName());

    public RestProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> send(@NonNull HttpMethod httpMethod, String url, Class<T> responseClassType) {
        RestHttpClient client = new RestHttpClient(httpMethod, url);
        return this.send(client, responseClassType);
    }

    public <T> ResponseEntity<T> send(@NonNull HttpMethod httpMethod, String url,
                                      HttpHeader headers, Class<T> responseClassType) {
        RestHttpClient client = new RestHttpClient(httpMethod, url, headers);
        return this.send(client, responseClassType);
    }

    public <T> ResponseEntity<T> send(@NonNull HttpMethod httpMethod, String url,
                                      MultiValueMap<String, String> params, Class<T> responseClassType) {
        RestHttpClient client = new RestHttpClient(httpMethod, url, params);
        return this.send(client, responseClassType);
    }

    public <T> ResponseEntity<T> send(@NonNull HttpMethod httpMethod, String url,
                                      MultiValueMap<String, String> params,
                                      HttpHeader headers, Class<T> responseClassType) {
        RestHttpClient client = new RestHttpClient(httpMethod, url, params, headers);
        return this.send(client, responseClassType);
    }

    public <T> ResponseEntity<T> send(RestHttpClient client, Class<T> responseClassType) {
        if (client.getMethod().equals(HttpMethod.GET)) {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(client.getUrl());
            if (client.getParams() != null) client.getParams().forEach(uriBuilder::queryParam);
            return restTemplate.getForEntity(uriBuilder.toUriString(), responseClassType);
        } else if (client.getMethod().equals(HttpMethod.POST)) {
            return restTemplate.exchange(client.getUrl(), HttpMethod.POST, client.toEntity(), responseClassType);
        } else if (client.getMethod().equals(HttpMethod.PUT)) {
            return restTemplate.exchange(client.getUrl(), HttpMethod.PUT, client.toEntity(), responseClassType);
        } else if (client.getMethod().equals(HttpMethod.PATCH)) {
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            return restTemplate.exchange(client.getUrl(), HttpMethod.PATCH, client.toEntity(), responseClassType);
        } else if (client.getMethod().equals(HttpMethod.DELETE)) {
            return restTemplate.exchange(client.getUrl(), HttpMethod.DELETE, client.toEntity(), responseClassType);
        } else {
            logger.info(String.format("Not yet implement Method : %s ", client.getMethod()));
            return null;
        }
    }
}