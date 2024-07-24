package com.example.demo;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;


import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.web.rest.EntityTestCrudProductCategory;
import com.example.demo.web.rest.TestCrudProductCategory;

@SpringBootTest
class DemoApplicationTests {

	private TestRestTemplate template = new TestRestTemplate();
	private TestCrudProductCategory test = new EntityTestCrudProductCategory();

	private HttpHeaders headers = new HttpHeaders();

	@Test
	void createProductAndCategory_thenCorrect() {
		test.testCreateProductCategory_thenCorrect(template, headers);
	}

	@Test
	void testUpdateProductAndCategory_thenCorrect() {
		test.testUpdateProductCategory_thenCorrect(template, headers);
	}

	@Test
	void testDeleteProduct_thenCorrect() {
		test.testDeleteProduct_thenCorrect(template, headers);
	}

	@Test
	void testDeleteCategory_thenCorrect() {
		test.testDeleteCategory_thenCorrect(template, headers);
	}

}
