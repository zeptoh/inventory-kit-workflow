package com.clevermoe.workflow.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import com.clevermoe.workflow.dto.InventoryManagement;

@Service
public class InventoryManagementService {

    private final RestTemplate restTemplate;

    public InventoryManagementService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public InventoryManagement execute(JSONObject input) {
        String url = "http://localhost:8080/api/inventorymanagement/product";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(input.toString(), headers);
        ResponseEntity<InventoryManagement> response = restTemplate.postForEntity(url, request,
                InventoryManagement.class);
        return response.getBody();
    }
}
