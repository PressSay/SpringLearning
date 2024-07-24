package com.example.demo.web.rest;

import org.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import com.example.demo.dto.request.CategoryRequest;
import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.dto.response.ProductResponse;

public class EntityTestCrudProductCategory implements TestCrudProductCategory {

    private static String PRODUCT_ENPOINT = "http://localhost:8080/products";
    private static String CATEGORY_ENPOINT = "http://localhost:8080/categories";

    private static String CATEGORY_NAME = "Books";

    private static String PRODUCT_NAME_1 = "citizen of SVN";
    private static String PRODUCT_DESC_1 = "about of anthem of RVN";
    private static Integer PRODUCT_QUANTITY_1 = 5;
    private static Double PRODUCT_PRICE_1 = 99.99;

    private static String PRODUCT_NAME_2 = "1963 in SVN";
    private static String PRODUCT_DESC_2 = "El golpe de 1963 en VN";
    private static Integer PRODUCT_QUANTITY_2 = 6;
    private static Double PRODUCT_PRICE_2 = 10.44;

    @SuppressWarnings("null")
    @Override
    public void testCreateProductCategory_thenCorrect(TestRestTemplate template, HttpHeaders headers) {

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName(CATEGORY_NAME);

        ResponseEntity<CategoryResponse> responseEntityCategory = template.postForEntity(CATEGORY_ENPOINT,
                categoryRequest, CategoryResponse.class);

        Assert.isTrue(responseEntityCategory.hasBody(), "Post Category failed");
        CategoryResponse categoryResponse = responseEntityCategory.getBody();

        ProductRequest productRequest1 = new ProductRequest();
        productRequest1.setCategoryId(categoryResponse.getId());
        productRequest1.setDesc(PRODUCT_DESC_1);
        productRequest1.setName(PRODUCT_NAME_1);
        productRequest1.setPrice(PRODUCT_PRICE_1);
        productRequest1.setQuantity(PRODUCT_QUANTITY_1);

        ProductRequest productRequest2 = new ProductRequest();
        productRequest2.setCategoryId(categoryResponse.getId());
        productRequest2.setDesc(PRODUCT_DESC_2);
        productRequest2.setName(PRODUCT_NAME_2);
        productRequest2.setPrice(PRODUCT_PRICE_2);
        productRequest2.setQuantity(PRODUCT_QUANTITY_2);

        ResponseEntity<ProductResponse> responseEntityProduct1 = template.postForEntity(PRODUCT_ENPOINT,
                productRequest1, ProductResponse.class);
        Assert.isTrue(responseEntityProduct1.hasBody(), "Post Product1 failed");

        ResponseEntity<ProductResponse> responseEntityProduct2 = template.postForEntity(PRODUCT_ENPOINT,
                productRequest2, ProductResponse.class);
        Assert.isTrue(responseEntityProduct2.hasBody(), "Post Product2 failed");
    }

    @SuppressWarnings("null")
    @Override
    public void testUpdateProductCategory_thenCorrect(TestRestTemplate template, HttpHeaders headers) {
        ResponseEntity<ProductResponse> responseEntityProduct1 = template.getForEntity(PRODUCT_ENPOINT + "/1",
                ProductResponse.class);
        Assert.isTrue(responseEntityProduct1.hasBody(), "Get Product1 failed");

        ResponseEntity<ProductResponse> responseEntityProduct2 = template.getForEntity(PRODUCT_ENPOINT + "/2",
                ProductResponse.class);
        Assert.isTrue(responseEntityProduct2.hasBody(), "Get Product2 failed");

        ProductResponse responseProduct1 = responseEntityProduct1.getBody();
        ProductResponse responseProduct2 = responseEntityProduct2.getBody();

        ProductRequest productRequest1 = new ProductRequest();
        productRequest1.setCategoryId(responseProduct1.getCategory().getId());
        productRequest1.setDesc(PRODUCT_DESC_1 + " updated");
        productRequest1.setName(PRODUCT_NAME_1);
        productRequest1.setPrice(PRODUCT_PRICE_1);
        productRequest1.setQuantity(PRODUCT_QUANTITY_1);

        ProductRequest productRequest2 = new ProductRequest();
        productRequest2.setCategoryId(responseProduct2.getCategory().getId());
        productRequest2.setDesc(PRODUCT_DESC_2 + " updated");
        productRequest2.setName(PRODUCT_NAME_2);
        productRequest2.setPrice(PRODUCT_PRICE_2);
        productRequest2.setQuantity(PRODUCT_QUANTITY_2);

        template.put(PRODUCT_ENPOINT + "/" + responseProduct1.getId(), productRequest1);
        template.put(PRODUCT_ENPOINT + "/" + responseProduct2.getId(), productRequest2);

        ResponseEntity<ProductResponse> responseEntityProduct1Updated = template.getForEntity(PRODUCT_ENPOINT + "/1",
                ProductResponse.class);
        Assert.isTrue(responseEntityProduct1Updated.hasBody(), "Get Product1 failed");

        ResponseEntity<ProductResponse> responseEntityProduct2Updated = template.getForEntity(PRODUCT_ENPOINT + "/2",
                ProductResponse.class);
        Assert.isTrue(responseEntityProduct2Updated.hasBody(), "Get Product2 failed");

        ProductResponse responseProduct1Updated = responseEntityProduct1Updated.getBody();
        ProductResponse responseProduct2Updated = responseEntityProduct2Updated.getBody();

        Assert.isTrue(responseProduct1Updated.getDesc().equals(PRODUCT_DESC_1 + " updated"),
                "product1 can't not updated");
        Assert.isTrue(responseProduct2Updated.getDesc().equals(PRODUCT_DESC_2 + " updated"),
                "product2 can't not updated");

        // Is category updated

        ResponseEntity<CategoryResponse> responseEntityCategory1 = template.getForEntity(PRODUCT_ENPOINT + "/1",
                CategoryResponse.class);
        Assert.isTrue(responseEntityCategory1.hasBody(), "Get Category failed");

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName(CATEGORY_NAME + " updated");

        template.put(CATEGORY_ENPOINT + "/" + responseEntityCategory1.getBody().getId(), categoryRequest);

        ResponseEntity<CategoryResponse> responseEntityCategory1Updated = template.getForEntity(
                CATEGORY_ENPOINT + "/" + responseEntityCategory1.getBody().getId(),
                CategoryResponse.class);
        Assert.isTrue(responseEntityCategory1Updated.hasBody(), "Get Category failed");

        CategoryResponse responseCategoryUpdated = responseEntityCategory1Updated.getBody();

        Assert.isTrue(responseCategoryUpdated.getName().equals(CATEGORY_NAME + " updated"),
                "category can't not updated");
    }

    @SuppressWarnings("null")
    @Override
    public void testDeleteProduct_thenCorrect(TestRestTemplate template, HttpHeaders headers) {
        ResponseEntity<ProductResponse> responseEntityProduct1 = template.getForEntity(PRODUCT_ENPOINT + "/1",
                ProductResponse.class);
        Assert.isTrue(responseEntityProduct1.hasBody(), "Get Product1 failed");

        ProductResponse productResponse1 = responseEntityProduct1.getBody();

        template.delete(PRODUCT_ENPOINT + "/" + productResponse1.getId());

        ResponseEntity<ProductResponse> responseEntityProduct1Deleted = template.getForEntity(
                PRODUCT_ENPOINT + "/" + productResponse1.getId(),
                ProductResponse.class);
        Assert.isTrue(responseEntityProduct1Deleted.getStatusCode().is4xxClientError(), "Delete Product1 failed");

        // Product 2

        ResponseEntity<ProductResponse> responseEntityProduct2 = template.getForEntity(PRODUCT_ENPOINT + "/2",
                ProductResponse.class);
        Assert.isTrue(responseEntityProduct2.hasBody(), "Get Product2 failed");

        ProductResponse productResponse2 = responseEntityProduct2.getBody();

        template.delete(PRODUCT_ENPOINT + "/" + productResponse2.getId());

        ResponseEntity<ProductResponse> responseEntityProduct2Deleted = template.getForEntity(
                PRODUCT_ENPOINT + "/" + productResponse2.getId(),
                ProductResponse.class);
        Assert.isTrue(responseEntityProduct2Deleted.getStatusCode().is4xxClientError(), "Delete Product2 failed");
    }

    @SuppressWarnings("null")
    @Override
    public void testDeleteCategory_thenCorrect(TestRestTemplate template, HttpHeaders headers) {
        ResponseEntity<CategoryResponse> responseEntityCategory = template.getForEntity(CATEGORY_ENPOINT + "/1",
                CategoryResponse.class);
        Assert.isTrue(responseEntityCategory.hasBody(), "Get Category failed");

        CategoryResponse categoryResponse = responseEntityCategory.getBody();

        template.delete(CATEGORY_ENPOINT + "/" + categoryResponse.getId());

        ResponseEntity<CategoryResponse> responseEntityCategoryDeleted = template.getForEntity(
                CATEGORY_ENPOINT + "/" + categoryResponse.getId(),
                CategoryResponse.class);
        Assert.isTrue(responseEntityCategoryDeleted.getStatusCode().is4xxClientError(), "Delete Category failed");
    }

}
