package com.example.demo.web.rest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;

public interface TestCrudProductCategory {
    
    void testCreateProductCategory_thenCorrect(TestRestTemplate template, HttpHeaders headers);

    void testUpdateProductCategory_thenCorrect(TestRestTemplate template, HttpHeaders headers);

    void testDeleteProduct_thenCorrect(TestRestTemplate template, HttpHeaders headers);

    void testDeleteCategory_thenCorrect(TestRestTemplate template, HttpHeaders headers);

}
