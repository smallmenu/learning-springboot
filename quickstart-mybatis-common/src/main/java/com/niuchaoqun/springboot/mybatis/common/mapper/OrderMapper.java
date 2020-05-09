package com.niuchaoqun.springboot.mybatis.common.mapper;

import com.niuchaoqun.springboot.mybatis.common.entity.Order;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrderMapper extends Mapper<Order> {
}
