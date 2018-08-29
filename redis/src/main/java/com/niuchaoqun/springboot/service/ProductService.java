package com.niuchaoqun.springboot.service;

import com.niuchaoqun.springboot.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long productId);

    List<Product> getProductWhere(Integer id);

    List<Product> getAllProduct();
}
