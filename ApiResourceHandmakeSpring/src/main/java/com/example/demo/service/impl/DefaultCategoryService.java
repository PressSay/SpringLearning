package com.example.demo.service.impl;

import com.example.demo.dto.request.CategoryRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCategoryService implements CategoryService {

  private final CategoryRepository repository;
  private final ProductService productService;

  public DefaultCategoryService(CategoryRepository repository, ProductService productService) {
    this.repository = repository;
    this.productService = productService;
  }

  @Override
  public List<ProductResponse> findProductResponsesByCategoryId(Long id) {
    Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " is not found"));
    List<Product> products = category.getProducts();

    List<ProductResponse> res = products.stream().map(p -> {
     return productService.toResponse(p);
    }).toList();

    return res;
  }

  @Override
  public CategoryResponse create(CategoryRequest request) {
    Category category = new Category();
    category.setName(request.getName());
    
    Category savedCategory = repository.save(category);
    CategoryResponse reponse = toResponse(savedCategory);

    return reponse;
  }

  @Override
  public CategoryResponse findById(Long id) {
    Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " is not found"));

    CategoryResponse response = new CategoryResponse();
    response.setId(category.getId());
    response.setName(category.getName());

    return response;
  }

  @Override
  public CategoryResponse update(Long id, CategoryRequest request) {
    Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " is not found"));

    Category updatedCategory = new Category();
    updatedCategory.setId(category.getId());
    updatedCategory.setName(request.getName());
    
    Category savedCategory = repository.save(updatedCategory);

    CategoryResponse response = toResponse(savedCategory);

    return response;
  }

  @Override
  public void deleteById(Long id) {
    Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " is not found"));
    repository.delete(category);
  }

  @Override
  public CategoryResponse toResponse(Category category) {
    CategoryResponse reponse = new CategoryResponse();
    reponse.setId(category.getId());
    reponse.setName(category.getName());

    return reponse;
  }
}
