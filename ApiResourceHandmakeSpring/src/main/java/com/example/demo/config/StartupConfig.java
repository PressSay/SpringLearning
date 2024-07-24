package com.example.demo.config;

import com.example.demo.entities.Category;
import com.example.demo.repositories.CategoryRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfig {

  @Bean
  public CommandLineRunner startup(CategoryRepository repository) {
    return args -> {
//      Category category = new Category();
//      category.setName("Dien thoai");
//      repository.save(category);
//
//      Category category2 = new Category();
//      category2.setName("Laptop");
//      repository.save(category2);
    };
  }

}
