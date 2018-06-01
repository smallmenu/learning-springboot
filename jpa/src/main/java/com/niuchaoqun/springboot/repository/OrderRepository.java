package com.niuchaoqun.springboot.repository;

import com.niuchaoqun.springboot.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
