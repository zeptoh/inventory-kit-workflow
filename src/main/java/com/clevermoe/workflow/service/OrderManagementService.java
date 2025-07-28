package com.clevermoe.workflow.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.clevermoe.workflow.dto.OrderSummary;

@Service
public class OrderManagementService {

    private final RestTemplate restTemplate;

    public OrderManagementService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OrderSummary execute(JSONObject input) {
        String url = "http://localhost:8080/api/ordermanagement";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(input.toString(), headers);
        ResponseEntity<OrderSummary> response = restTemplate.postForEntity(url, request,
                OrderSummary.class);
        return response.getBody();
    }
}
