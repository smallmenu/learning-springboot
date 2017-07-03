package com.niuchaoqun.jpa.repository;

import com.niuchaoqun.jpa.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
