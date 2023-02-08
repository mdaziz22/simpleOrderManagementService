package com.task.orderManagementSystem.SimpleOrderManagementSystem.repository;

import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
