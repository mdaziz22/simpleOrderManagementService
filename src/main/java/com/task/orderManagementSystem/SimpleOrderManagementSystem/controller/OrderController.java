package com.task.orderManagementSystem.SimpleOrderManagementSystem.controller;

import com.task.orderManagementSystem.SimpleOrderManagementSystem.dto.OrderDto;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.Orders;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /*
     * Create a new order based on the info provided by the customer
     */
    @PostMapping("/order/{customerId}")
    public ResponseEntity<Orders> createOrder(@PathVariable long customerId, @RequestBody OrderDto orderDto) {
        try {
            return new ResponseEntity<>(orderService.createOrder(customerId, orderDto), HttpStatus.OK);
        } catch (Exception ex) {
            ex.getStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Get all orders
     */
    @GetMapping("/order")
    public ResponseEntity<List<Orders>> getAllOrders() {
        try {
            return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.getStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Get all orders for a given customerId provided by user
     */
    @GetMapping("/order/{customerId}")
    public ResponseEntity<List<Orders>> getAllOrdersForCustomerId(@PathVariable long customerId) {
        try {
            return new ResponseEntity<>(orderService.getAllOrdersForCustomerId(customerId), HttpStatus.OK);
        } catch (Exception ex) {
            ex.getStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Delete customer for a given customerId provided by user
     */
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<String> deleteOrders(@PathVariable long orderId) {
        try {
            return new ResponseEntity<>(orderService.deleteOrderById(orderId), HttpStatus.OK);
        } catch (Exception ex) {
            ex.getStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Update order details provided by user
     */
    @PutMapping("/order/{orderId}")
    public ResponseEntity<Orders> updateOrders(@PathVariable long orderId, @RequestBody OrderDto orderDto) {
        try {
            return new ResponseEntity<>(orderService.updateOrderDetails(orderId, orderDto), HttpStatus.OK);
        } catch (Exception ex) {
            ex.getStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
