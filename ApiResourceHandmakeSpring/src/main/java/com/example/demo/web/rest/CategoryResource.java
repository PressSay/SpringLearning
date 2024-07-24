package com.example.demo.web.rest;

import com.example.demo.dto.request.CategoryRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.service.CategoryService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/categories")
public class CategoryResource {

  private final CategoryService categoryService;

  public CategoryResource(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/{id}/products")
  public List<ProductResponse> findProductResponsesByCategoryId(@PathVariable Long id) {
    return categoryService.findProductResponsesByCategoryId(id);
  }

  //VIET 4 APIs CRUD
  @PostMapping
  public CategoryResponse create(@RequestBody CategoryRequest request) {
    CategoryResponse categoryResponse = categoryService.create(request);
    return categoryResponse;
  }

  @GetMapping("/{id}")
  public CategoryResponse findById(@PathVariable Long id) {
      return categoryService.findById(id);
  }
  

  @PutMapping("/{id}")
  public CategoryResponse update(@PathVariable Long id, @RequestBody CategoryRequest request) {
    CategoryResponse categoryResponse = categoryService.update(id, request);
    return categoryResponse;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) {
    categoryService.deleteById(id);
  }

}
