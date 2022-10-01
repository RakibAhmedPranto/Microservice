package com.rakib.product.service;

import com.rakib.product.dto.ProductRequest;
import com.rakib.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProduct();
}
