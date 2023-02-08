package com.task.orderManagementSystem.SimpleOrderManagementSystem.controller;

import com.task.orderManagementSystem.SimpleOrderManagementSystem.dto.CustomerDto;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.dto.OrderDto;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.Customer;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.OrderStatus;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.Orders;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.services.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void createOrderSuccessTest() {
        long customerId = 1234;
        OrderDto orderDto = getOrderDto();
        Orders orders = getOrder(orderDto);
        ResponseEntity<Orders> expectedOutput = new ResponseEntity<>(orders, HttpStatus.OK);
        Mockito.when(orderService.createOrder(customerId, orderDto)).thenReturn(orders);
        ResponseEntity<Orders> actualOutput = orderController.createOrder(customerId, orderDto);
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void createOrderFailureTest() {
        long customerId = 1234;
        OrderDto orderDto = getOrderDto();
        ResponseEntity<Orders> expectedOutput = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Mockito.when(orderService.createOrder(customerId, orderDto)).thenThrow(new RuntimeException());
        ResponseEntity<Orders> actualOutput = orderController.createOrder(customerId, orderDto);
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void getAllOrdersSuccessTest() {
        OrderDto orderDto = getOrderDto();
        List<Orders> orders = new ArrayList<>();
        orders.add(getOrder(orderDto));
        ResponseEntity<List<Orders>> expectedOutput = new ResponseEntity<>(orders, HttpStatus.OK);
        Mockito.when(orderService.getAllOrders()).thenReturn(orders);
        ResponseEntity<List<Orders>> actualOutput = orderController.getAllOrders();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getAllOrdersFailureTest() {
        OrderDto orderDto = getOrderDto();
        List<Orders> orders = new ArrayList<>();
        orders.add(getOrder(orderDto));
        ResponseEntity<List<Orders>> expectedOutput = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Mockito.when(orderService.getAllOrders()).thenThrow(new RuntimeException());
        ResponseEntity<List<Orders>> actualOutput = orderController.getAllOrders();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getAllOrdersForCustomerIdSuccessTest() {
        long customerId = 1234;
        OrderDto orderDto = getOrderDto();
        List<Orders> orders = new ArrayList<>();
        orders.add(getOrder(orderDto));
        ResponseEntity<List<Orders>> expectedOutput = new ResponseEntity<>(orders, HttpStatus.OK);
        Mockito.when(orderService.getAllOrdersForCustomerId(customerId)).thenReturn(orders);
        ResponseEntity<List<Orders>> actualOutput = orderController.getAllOrdersForCustomerId(customerId);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getAllOrdersForCustomerIdFailureTest() {
        long customerId = 1234;
        ResponseEntity<List<Orders>> expectedOutput = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Mockito.when(orderService.getAllOrdersForCustomerId(customerId)).thenThrow(new RuntimeException());
        ResponseEntity<List<Orders>> actualOutput = orderController.getAllOrdersForCustomerId(customerId);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void deleteOrdersSuccessTest() {
        long orderId = 1;
        String response = "Deleted order with order id - " + orderId;
        ResponseEntity<String> expectedOutput = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(orderService.deleteOrderById(orderId)).thenReturn(response);
        ResponseEntity<String> actualOutput = orderController.deleteOrders(orderId);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void deleteOrdersFailureTest() {
        long orderId = 1;
        ResponseEntity<String> expectedOutput = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Mockito.when(orderService.deleteOrderById(orderId)).thenThrow(new RuntimeException());
        ResponseEntity<String> actualOutput = orderController.deleteOrders(orderId);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void updateOrdersSuccessTest() {
        long orderId = 1234;
        OrderDto orderDto = getOrderDto();
        Orders orders = getOrder(orderDto);
        ResponseEntity<Orders> expectedOutput = new ResponseEntity<>(orders, HttpStatus.OK);
        Mockito.when(orderService.updateOrderDetails(orderId, orderDto)).thenReturn(orders);
        ResponseEntity<Orders> actualOutput = orderController.updateOrders(orderId, orderDto);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void updateOrdersFailureTest() {
        long orderId = 1234;
        OrderDto orderDto = getOrderDto();
        ResponseEntity<Orders> expectedOutput = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Mockito.when(orderService.updateOrderDetails(orderId, orderDto)).thenThrow(new RuntimeException());
        ResponseEntity<Orders> actualOutput = orderController.updateOrders(orderId, orderDto);
        assertEquals(expectedOutput, actualOutput);
    }

    private OrderDto getOrderDto() {
        return OrderDto.builder()
                .orderAmount(1000)
                .build();
    }

    private Orders getOrder(OrderDto orderDto) {
        return Orders.builder()
                .orderAmount(orderDto.getOrderAmount())
                .orderStatus(OrderStatus.ORDER_PLACED)
                .build();
    }
}