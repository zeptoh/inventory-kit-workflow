package com.clevermoe.workflow.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.clevermoe.workflow.dto.Customer;

@Service
public class CustomerService {

    private final RestTemplate restTemplate;

    public CustomerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Customer execute(JSONObject input) {
        String url = "http://localhost:8080/api/customerprofile/find";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(input.toString(), headers);
        ResponseEntity<Customer> response = restTemplate.postForEntity(url, request,
                Customer.class);
        return response.getBody();
    }
}
