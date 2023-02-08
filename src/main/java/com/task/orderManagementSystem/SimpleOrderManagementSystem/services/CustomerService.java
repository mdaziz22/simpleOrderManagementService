package com.task.orderManagementSystem.SimpleOrderManagementSystem.services;

import com.task.orderManagementSystem.SimpleOrderManagementSystem.dto.CustomerDto;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.Customer;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.CustomerMembership;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = Customer.builder()
                .customerId(customerDto.getCustomerId())
                .customerName(customerDto.getCustomerName())
                .customerAddress(customerDto.getCustomerAddress())
                .customerContact(customerDto.getCustomerContact())
                .membership(CustomerMembership.REGULAR)
                .build();
        return customerRepository.save(customer);
    }

    public Customer getCustomerByCustomerId(long customerId) {
        try {
            return customerRepository.findById(customerId).get();
        } catch (Exception ex) {
            throw new NoSuchElementException("Order not found");
        }
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public String deleteCustomer(long customerId) {
        try {
            customerRepository.deleteById(customerId);
            return "Customer with customer id - " + customerId + " deleted successfully";
        } catch (Exception ex) {
            throw new NoSuchElementException("CustomerId not found");
        }
    }

    public Customer updateCustomerDetails(long customerId, CustomerDto customerDto) {
        try {
            Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
            optionalCustomer.get().setCustomerName(customerDto.getCustomerName());
            optionalCustomer.get().setCustomerAddress(customerDto.getCustomerAddress());
            optionalCustomer.get().setCustomerContact(customerDto.getCustomerContact());
            return customerRepository.save(optionalCustomer.get());
        } catch (Exception ex) {
            throw new NoSuchElementException("CustomerId not found");
        }
    }

}