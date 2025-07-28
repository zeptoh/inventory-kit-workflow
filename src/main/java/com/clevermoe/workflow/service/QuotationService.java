package com.clevermoe.workflow.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.clevermoe.workflow.dto.Quotation;

@Service
public class QuotationService {

    private final RestTemplate restTemplate;

    public QuotationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Quotation execute(JSONObject input) {
        String url = "http://localhost:8080/api/quotation";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(input.toString(), headers);
        ResponseEntity<Quotation> response = restTemplate.postForEntity(url, request,
                Quotation.class);
        return response.getBody();
    }
}
