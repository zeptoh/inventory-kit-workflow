package com.clevermoe.workflow.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.clevermoe.workflow.dto.TariffCustoms;

@Service
public class TariffCustomsService {

    private final RestTemplate restTemplate;

    public TariffCustomsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TariffCustoms execute(JSONObject input) {
        String url = "http://localhost:8080/api/tariffcustoms/product";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(input.toString(), headers);
        ResponseEntity<TariffCustoms> response = restTemplate.postForEntity(url, request,
                TariffCustoms.class);
        return response.getBody();
    }
}
