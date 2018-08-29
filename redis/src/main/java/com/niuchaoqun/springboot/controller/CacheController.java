package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.core.BaseController;
import com.niuchaoqun.springboot.core.Response;
import com.niuchaoqun.springboot.entity.Product;
import com.niuchaoqun.springboot.mapper.ProductMapper;
import com.niuchaoqun.springboot.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cache")
public class CacheController extends BaseController {
    public static final Logger logger = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @RequestMapping("/product")
    public Object products(@RequestParam(value = "id", required = false) Integer id){
        if (id != null && id > 0) {
            List<Product> productWhere = productService.getProductWhere(id);
            return Response.data(productWhere);
        } else {
            List<Product> allProduct = productService.getAllProduct();
            return Response.data(allProduct);
        }
    }

    @RequestMapping("/product/{productId}")
    public Object get(@PathVariable Long productId) {
        if (productId > 0) {
            Product product = productService.getProductById(productId);
            if (product != null) {
                return Response.data(product);
            }
        }
        return Response.error();
    }

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.PUT)
    public Object save(@PathVariable Long productId) {
        if (productId > 0) {
            Product product = productMapper.selectByPrimaryKey(productId);
            product.setPrice(0.00);
            Product save = productService.save(product);
            return Response.data(save);
        }
        return Response.error();
    }
}
