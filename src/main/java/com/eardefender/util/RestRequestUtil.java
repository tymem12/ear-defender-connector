package com.eardefender.util;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

import static org.springframework.http.HttpMethod.POST;

public class RestRequestUtil {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public static <T> ResponseEntity<Void> sendPostRequestWithAuth(String url,
                                                   T body,
                                                   HttpServletRequest request,
                                                   RestTemplate restTemplate,
                                                   Logger logger) {

        logger.info("Sending request to {}", url);

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION_HEADER, getToken(request));

        HttpEntity<T> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                POST,
                requestEntity,
                Void.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Request to sent successfully to {}, request body: {}", url, body.toString());
        } else {
            logger.error("Failed to sent request to {}. Response status: {}, Request body: {}", url, response.getStatusCode(), body.toString());
        }

        return response;
    }

    public static <T> ResponseEntity<Void> sendPostRequestWithAuth(String url,
                                                                   T body,
                                                                   HttpServletRequest request,
                                                                   RestTemplate restTemplate,
                                                                   Logger logger,
                                                                   Consumer<ResponseEntity<Void>> onSuccess,
                                                                   Consumer<ResponseEntity<Void>> onFailure) {

        ResponseEntity<Void> response = sendPostRequestWithAuth(url, body, request, restTemplate, logger);

        if (response.getStatusCode().is2xxSuccessful()) {
            onSuccess.accept(response);
        } else {
            onFailure.accept(response);
        }

        return response;
    }

    private static String getToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }
}
