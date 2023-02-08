package com.task.orderManagementSystem.SimpleOrderManagementSystem.services;

import com.task.orderManagementSystem.SimpleOrderManagementSystem.dto.CustomerDto;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.dto.OrderDto;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.Customer;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.CustomerMembership;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.OrderStatus;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.Orders;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.repository.CustomerRepository;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder() {
        long customerId = 1234;
        OrderDto orderDto = getOrderDto();
        Orders orders = getOrder(orderDto);
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(orders);
        Customer customer = getCustomer(customerId, ordersList);
        Mockito.when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));
        Mockito.when(orderRepository.save(any())).thenReturn(orders);
        Orders actualOutput = orderService.createOrder(customerId, orderDto);
        assertEquals(orders, actualOutput);

    }

    @Test
    void getAllOrdersForCustomerIdSuccessTest() {
        long orderId = 1;
        long customerId = 1234;
        OrderDto orderDto = getOrderDto();
        Orders orders = getOrder(orderDto);
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(orders);
        Customer customer = getCustomer(customerId, ordersList);
        customer.setOrders(ordersList);
        Mockito.when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        List<Orders> actualOutput = orderService.getAllOrdersForCustomerId(orderId);
        assertEquals(ordersList, actualOutput);
    }

    @Test
    void getAllOrdersForCustomerIdFailureTest() {
        long orderId = 1;
        Mockito.when(customerRepository.findById(any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> orderService.getAllOrdersForCustomerId(orderId));
    }

    @Test
    void getAllOrders() {
        OrderDto orderDto = getOrderDto();
        Orders orders = getOrder(orderDto);
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(orders);
        Mockito.when(orderRepository.findAll()).thenReturn(ordersList);
        List<Orders> actualOutput = orderService.getAllOrders();
        assertEquals(ordersList, actualOutput);
    }

    @Test
    void deleteOrderByIdSuccessTest() {
        long orderId = 1;
        String expectedOutput = "Deleted order with order id - " + orderId;
        Mockito.doNothing().when(orderRepository).deleteById(any());
        String actualOutput = orderService.deleteOrderById(orderId);
        Mockito.verify(orderRepository, Mockito.times(1)).deleteById(orderId);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void deleteOrderByIdFailureTest() {
        long orderId = 1;
        Mockito.doThrow(new RuntimeException()).when(orderRepository).deleteById(orderId);
        assertThrows(RuntimeException.class, () -> orderService.deleteOrderById(orderId));
    }

    @Test
    void updateOrderDetails() {
        long orderId = 1;
        long customerId = 1234;
        OrderDto orderDto = getOrderDto();
        Orders orders = getOrder(orderDto);
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(orders);
        orders.setCustomer(getCustomer(customerId, ordersList));
        Mockito.when(orderRepository.findById(any())).thenReturn(Optional.of(orders));
        Mockito.when(orderRepository.save(any())).thenReturn(orders);
        Orders actualOutput = orderService.updateOrderDetails(orderId, orderDto);
        assertEquals(orders, actualOutput);

    }

    private OrderDto getOrderDto() {
        return OrderDto.builder()
                .orderAmount(1000)
                .build();
    }

    private Orders getOrder(OrderDto orderDto) {
        return Orders.builder()
                .orderId(1)
                .orderAmount(orderDto.getOrderAmount())
                .orderStatus(OrderStatus.ORDER_PLACED)
                .build();
    }

    private static Customer getCustomer(long customerId, List<Orders> ordersList) {
        return Customer.builder()
                .customerId(customerId)
                .customerName("Tom")
                .customerContact("876543")
                .customerAddress("New York")
                .membership(CustomerMembership.REGULAR)
                .orders(ordersList)
                .build();
    }
}