package com.clevermoe.workflow.service;

import java.util.HashMap;
import java.util.Map;

import org.bson.json.JsonObject;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.clevermoe.workflow.dto.PricingEngine;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PricingEngineService {

    private final RestTemplate restTemplate;

    public PricingEngineService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PricingEngine execute(JSONObject input) {
        String url = "http://localhost:8080/api/pricingengine/product";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(input.toString(), headers);
        ResponseEntity<PricingEngine> response = restTemplate.postForEntity(url, request,
                PricingEngine.class);
        return response.getBody();
    }
}
