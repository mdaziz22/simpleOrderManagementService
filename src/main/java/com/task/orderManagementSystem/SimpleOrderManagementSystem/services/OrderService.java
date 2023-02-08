package com.task.orderManagementSystem.SimpleOrderManagementSystem.services;

import com.task.orderManagementSystem.SimpleOrderManagementSystem.dto.OrderDto;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.Customer;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.CustomerMembership;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.OrderStatus;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.Orders;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.repository.CustomerRepository;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Orders createOrder(long customerId, OrderDto orderDto) {
        try {
            Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
            double discount = 0.0;
            CustomerMembership membership = CustomerMembership.REGULAR;
            if (optionalCustomer.get().getOrders().size() >= 10 && optionalCustomer.get().getOrders().size() < 20) {
                membership = CustomerMembership.GOLD;
                discount = orderDto.getOrderAmount() * 0.10;
            } else if (optionalCustomer.get().getOrders().size() >= 20) {
                membership = CustomerMembership.PLATINUM;
                discount = orderDto.getOrderAmount() * 0.20;
            }
            Orders orders = createNewOrder(orderDto, optionalCustomer, discount, membership);
            customerRepository.save(optionalCustomer.get());
            return orderRepository.save(orders);
        } catch (Exception ex) {
            throw new NoSuchElementException("Customer not found");
        }
    }

    private static Orders createNewOrder(OrderDto orderDto, Optional<Customer> optionalCustomer, double discount, CustomerMembership membership) {
        optionalCustomer.get().setMembership(membership);
        Orders orders = Orders.builder()
                .orderId(orderDto.getOrderId())
                .orderAmount(orderDto.getOrderAmount())
                .orderDiscount(discount)
                .orderStatus(OrderStatus.ORDER_PLACED)
                .customer(optionalCustomer.get())
                .build();
        return orders;
    }

    public List<Orders> getAllOrdersForCustomerId(long customerId) {
        try {
            return customerRepository.findById(customerId).get().getOrders();
        } catch (Exception ex) {
            throw new NoSuchElementException("CustomerId not found");
        }
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public String deleteOrderById(long orderId) {
        try {
            orderRepository.deleteById(orderId);
            return "Deleted order with order id - " + orderId;
        } catch (Exception ex) {
            throw new NoSuchElementException("OrderId not found");
        }
    }

    public Orders updateOrderDetails(long orderId, OrderDto orderDto) {
        try {
            Optional<Orders> order = orderRepository.findById(orderId);
            double discount = getDiscount(orderDto, order);
            order.get().setOrderAmount(orderDto.getOrderAmount());
            order.get().setOrderDiscount(discount);
            return orderRepository.save(order.get());
        } catch (Exception ex) {
            throw new NoSuchElementException("OrderId not found");
        }
    }

    private static double getDiscount(OrderDto orderDto, Optional<Orders> order) {
        double discount = 0.0;
        if (order.get().getCustomer().getMembership().equals(CustomerMembership.GOLD)) {
            discount = orderDto.getOrderAmount() * 0.10;
        } else if (order.get().getCustomer().getMembership().equals(CustomerMembership.PLATINUM)) {
            discount = orderDto.getOrderAmount() * 0.20;
        }
        return discount;
    }

}
