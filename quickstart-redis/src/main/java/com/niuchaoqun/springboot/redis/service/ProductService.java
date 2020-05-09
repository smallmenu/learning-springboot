package com.niuchaoqun.springboot.redis.service;

import com.niuchaoqun.springboot.redis.entity.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    Product getProductById(Long productId);

    List<Product> getProductWhere(Integer id);

    List<Product> getAllProduct();
}
