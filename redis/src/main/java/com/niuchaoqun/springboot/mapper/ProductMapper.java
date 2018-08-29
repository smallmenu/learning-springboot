package com.niuchaoqun.springboot.mapper;

import com.niuchaoqun.springboot.entity.Product;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ProductMapper extends Mapper<Product> {
}
