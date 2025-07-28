package com.clevermoe.workflow.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.clevermoe.workflow.dto.ProductCatalog;

@Service
public class ProductCatalogService {

    private final RestTemplate restTemplate;

    public ProductCatalogService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductCatalog[] execute() {
        String url = "http://localhost:8080/api/productcatalog";
        ResponseEntity<ProductCatalog[]> response = restTemplate.getForEntity(url, ProductCatalog[].class);
        return response.getBody();
    }
}
