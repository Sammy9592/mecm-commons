package com.sl.mecm.core.commons.http;

import com.sl.mecm.core.commons.exception.MECMHttpException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public abstract class HttpService {

    protected abstract RestTemplate thisRestTemplate();

    protected abstract WebClient thisWebClient();

    protected String post(String url, Object requestBody, Map<String, String> headers){
        MultiValueMap<String, String> headerMap = this.formatHeaders(headers);
        URI uri = URI.create(url);
        RequestEntity<String> requestEntity = new RequestEntity<>(requestBody.toString(), headerMap, HttpMethod.POST, uri);
        try {
            ResponseEntity<String> responseEntity = this.thisRestTemplate().exchange(requestEntity, String.class);
            return responseEntity.getBody();
        }catch (RestClientResponseException e){
            String responseBody = e.getResponseBodyAsString();
            log.error("RestClientResponseException -> " + e.getMessage(), e);
            log.error("error response -> " + responseBody, e);
            throw new MECMHttpException("503", e.getStatusCode(), responseBody, e);
        }catch (ResourceAccessException e){
            log.error("ResourceAccessException -> " + e.getMessage(), e);
            throw new MECMHttpException("503", HttpStatus.NOT_FOUND, e.getMessage(), e);
        }catch (Exception e){
            log.error("Exception -> " + e.getMessage(), e);
            throw new MECMHttpException("503", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    protected Mono<String> reactivePost(String url, Object requestBody, Map<String, String> headers){
        return thisWebClient()
                .post()
                .uri(URI.create(url))
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(statusCode -> !statusCode.is2xxSuccessful(),
                        clientResponse -> clientResponse.toEntity(String.class)
                                .map(entity -> new MECMHttpException("503", entity.getStatusCode(), entity.getBody()))
                )
                .bodyToMono(String.class)
                .doOnNext(responseBody -> log.info("response:" + responseBody));
    }

    private MultiValueMap<String, String> formatHeaders(Map<String, String> headers){
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        headers.forEach(map::add);
        return map;
    }
}
