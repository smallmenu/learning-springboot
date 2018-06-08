package com.niuchaoqun.springboot.mapper;

import com.niuchaoqun.springboot.entity.Order;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrderMapper extends Mapper<Order> {
}
