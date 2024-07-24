package com.example.demo.web.rest;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductResource {

  private final ProductService service;

  public ProductResource(ProductService service) {
    this.service = service;
  }

  @PostMapping
  public ProductResponse create(@RequestBody ProductRequest request) {
    return service.create(request);
  }

  @GetMapping("/{id}")
  public ProductResponse findById(@PathVariable Long id) {
    return service.findById(id);
  }

  @PutMapping("/{id}")
  public /*ResponseEntity<*/ProductResponse/*>*/ updateById(@PathVariable Long id, @RequestBody ProductRequest request) {
    return service.updateById(id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) {
    service.deleteById(id);
  }

}
