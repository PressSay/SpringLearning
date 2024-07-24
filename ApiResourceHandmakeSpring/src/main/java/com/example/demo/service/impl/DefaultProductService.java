package com.example.demo.service.impl;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultProductService implements ProductService {

  private final ProductRepository repository;
  private final CategoryRepository categoryRepository;

  public DefaultProductService(ProductRepository repository, CategoryRepository categoryRepository) {
    this.repository = repository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public ProductResponse create(ProductRequest request) {
    Category categoryRef = categoryRepository.getReferenceById(request.getCategoryId());
    try {
      categoryRef.getId();
    } catch (EntityNotFoundException e) {
      System.err.println(e.getMessage());
      throw new BadRequestException(e.getMessage(), e);
    }

    Product product = new Product();
    product.setName(request.getName());
    product.setDesc(request.getDesc());
    product.setQuantity(request.getQuantity());
    product.setPrice(request.getPrice());
    product.setCategory(categoryRef);

    //save xuống database
    Product savedProduct = repository.save(product);

    ProductResponse response = toResponse(savedProduct);

    return response;
  }

  @Override
  public ProductResponse toResponse(Product product) {
    ProductResponse response = new ProductResponse();
    response.setId(product.getId());
    response.setName(product.getName());
    response.setDesc(product.getDesc());
    response.setQuantity(product.getQuantity());
    response.setPrice(product.getPrice());

    CategoryResponse categoryResponse = new CategoryResponse();
    categoryResponse.setId(product.getCategory().getId());
    categoryResponse.setName(product.getCategory().getName());

    response.setCategory(categoryResponse);
    return response;
  }

  @Override
  public ProductResponse findById(Long id) {
    Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " is not found"));

    CategoryResponse categoryResponse = new CategoryResponse();
    categoryResponse.setId(product.getCategory().getId());
    categoryResponse.setName(product.getCategory().getName());

    ProductResponse response = new ProductResponse();
    response.setId(product.getId());
    response.setName(product.getName());
    response.setDesc(product.getDesc());
    response.setQuantity(product.getQuantity());
    response.setPrice(product.getPrice());
    response.setCategory(categoryResponse);

    return response;
  }

  @Override
  public ProductResponse updateById(Long id, ProductRequest request) {
    //VIẾT CODE VÀO ĐÂY
    //TÌM TỪ DB RA, KO CÓ THÌ QUĂNG 404
    //NẾU CÓ THÌ TRANSFER TỪ request sang product
    //SAVE
    //TRANSFER QUA ProductResponse
    //RETURN ProductResponse
    Category categoryRef = categoryRepository.getReferenceById(request.getCategoryId());
    try {
      categoryRef.getId();
    } catch (EntityNotFoundException e) {
      System.err.println(e.getMessage());
      throw new BadRequestException(e.getMessage(), e);
    }

    Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " is not found"));

    Product updatedProduct = new Product();
    updatedProduct.setCategory(categoryRef);
    updatedProduct.setDesc(request.getDesc());
    updatedProduct.setId(product.getId());
    updatedProduct.setName(product.getName());
    updatedProduct.setPrice(product.getPrice());
    updatedProduct.setQuantity(product.getQuantity());

    Product saveProduct = repository.save(updatedProduct);
    ProductResponse response = toResponse(saveProduct);

    return response;
  }

  @Override
  public void deleteById(Long id) {
    Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " is not found"));
    repository.delete(product);
  }

}
