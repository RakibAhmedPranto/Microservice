package com.rakib.product.service;

import com.rakib.product.dto.ProductRequest;
import com.rakib.product.dto.ProductResponse;
import com.rakib.product.model.Product;
import com.rakib.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = this.modelMapper.map(productRequest,Product.class);
        Product savedProduct = this.productRepository.save(product);
        log.info("Product: "+savedProduct.getId()+" Saved");
        return this.modelMapper.map(product,ProductResponse.class);
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        List<Product> productList = this.productRepository.findAll();
        return productList.stream().map((product)->this.modelMapper.map(product,ProductResponse.class)).collect(Collectors.toList());
    }
}
