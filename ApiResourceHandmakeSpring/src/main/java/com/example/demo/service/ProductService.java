package com.example.demo.service;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entities.Product;

public interface ProductService {

  ProductResponse create(ProductRequest request);

  ProductResponse findById(Long id);

  ProductResponse updateById(Long id, ProductRequest request);

  void deleteById(Long id);

  ProductResponse toResponse(Product product);

}
