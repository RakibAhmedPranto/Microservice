package com.rakib.product.controller;

import com.rakib.product.dto.ProductRequest;
import com.rakib.product.dto.ProductResponse;
import com.rakib.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        ProductResponse productResponse = this.productService.createProduct(productRequest);
        return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductResponse>> getProducts(){
        List<ProductResponse> allProduct = this.productService.getAllProduct();
        return new ResponseEntity<List<ProductResponse>>(allProduct, HttpStatus.OK);
    }
}
