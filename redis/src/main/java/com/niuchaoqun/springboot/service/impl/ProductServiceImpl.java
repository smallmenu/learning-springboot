package com.niuchaoqun.springboot.service.impl;

import com.niuchaoqun.springboot.entity.Product;
import com.niuchaoqun.springboot.mapper.ProductMapper;
import com.niuchaoqun.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

@Service
@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    @Cacheable
    public Product getProductById(Long productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        return product;
    }

    @Override
    @Cacheable(value = "product_where")
    public List<Product> getProductWhere(Integer id) {
        Condition condition = new Condition(Product.class);
        condition.createCriteria();

        condition.and().andGreaterThan("id", id);
        condition.orderBy("id").desc();

        List<Product> products = productMapper.selectByExample(condition);
        return products;
    }

    @Override
    @Cacheable
    public List<Product> getAllProduct() {
        List<Product> products = productMapper.selectAll();
        return products;
    }
}
