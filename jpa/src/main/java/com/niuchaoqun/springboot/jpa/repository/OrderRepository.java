package com.niuchaoqun.springboot.jpa.repository;

import com.niuchaoqun.springboot.jpa.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
