package com.example.demo.service;

import com.example.demo.dto.request.CategoryRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entities.Category;

import java.util.List;

public interface CategoryService {

  List<ProductResponse> findProductResponsesByCategoryId(Long id);

  CategoryResponse create(CategoryRequest request);

  CategoryResponse findById(Long id);

  CategoryResponse update(Long id, CategoryRequest request);

  void deleteById(Long id);

  CategoryResponse toResponse(Category category);

}
