package com.task.orderManagementSystem.SimpleOrderManagementSystem.repository;

import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
