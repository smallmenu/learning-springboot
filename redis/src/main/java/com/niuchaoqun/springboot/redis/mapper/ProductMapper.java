package com.niuchaoqun.springboot.redis.mapper;

import com.niuchaoqun.springboot.redis.entity.Product;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ProductMapper extends Mapper<Product> {
}
