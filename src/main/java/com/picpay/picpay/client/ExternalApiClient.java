package com.picpay.picpay.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.picpay.picpay.exceptions.ExternalApiClientException;

@Component
public class ExternalApiClient {

    private final RestTemplate restTemplate;

    public ExternalApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void authorizeTransfer() {
        String url = "https://util.devi.tools/api/v2/authorize";
        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(url, ApiResponse.class);
        
        if (response == null || !"success".equals(response.getBody().getStatus()) || 
        !response.getBody().getData().isAuthorization()) {
        throw new ExternalApiClientException("Authorization failed: " + 
            (response != null ? response.getBody().getStatus() : "null response"));
        }
    }
}