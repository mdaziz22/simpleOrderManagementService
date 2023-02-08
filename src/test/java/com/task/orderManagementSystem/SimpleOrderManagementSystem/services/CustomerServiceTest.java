package com.task.orderManagementSystem.SimpleOrderManagementSystem.services;

import com.task.orderManagementSystem.SimpleOrderManagementSystem.dto.CustomerDto;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.Customer;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.CustomerMembership;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createCustomer() {
        CustomerDto customerDto = getCustomerDto();
        Customer customer = getCustomer(customerDto);
        Mockito.when(customerRepository.save(any())).thenReturn(customer);
        Customer actualOutput = customerService.createCustomer(customerDto);
        assertEquals(customer, actualOutput);
    }

    @Test
    void getCustomerByCustomerIdSuccessTest() {
        long customerId = 1234;
        CustomerDto customerDto = getCustomerDto();
        Customer customer = getCustomer(customerDto);
        Mockito.when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));
        Customer actualOutput = customerService.getCustomerByCustomerId(customerId);
        assertEquals(customer, actualOutput);
    }

    @Test
    void getCustomerByCustomerIdFailureTest() {
        long customerId = 1234;
        Mockito.when(customerRepository.findById(any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> customerService.getCustomerByCustomerId(customerId));
    }

    @Test
    void getAllCustomers() {
        CustomerDto customerDto = getCustomerDto();
        List<Customer> customers = new ArrayList<>();
        customers.add(getCustomer(customerDto));
        Mockito.when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> actualOutput = customerService.getAllCustomers();
        assertEquals(customers, actualOutput);
    }

    @Test
    void deleteCustomerSuccessTest() {
        long customerId = 1234;
        Mockito.doNothing().when(customerRepository).deleteById(any());
        customerService.deleteCustomer(customerId);
        Mockito.verify(customerRepository, Mockito.times(1)).deleteById(customerId);
    }

    @Test
    void deleteCustomerFailureTest() {
        long customerId = 1234;
        Mockito.doThrow(new RuntimeException()).when(customerRepository).deleteById(customerId);
        assertThrows(RuntimeException.class, () -> customerService.deleteCustomer(customerId));
    }

    @Test
    void updateCustomerDetailsSuccessTest() {
        long customerId = 1234;
        CustomerDto customerDto = getCustomerDto();
        Customer customer = getCustomer(customerDto);
        Mockito.when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));
        Mockito.when(customerRepository.save(any())).thenReturn(customer);
        Customer actualOutput = customerService.updateCustomerDetails(customerId, customerDto);
        assertEquals(customer, actualOutput);
    }

    @Test
    void updateCustomerDetailsFailureTest() {
        long customerId = 1234;
        CustomerDto customerDto = getCustomerDto();
        Customer customer = getCustomer(customerDto);
        Mockito.when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));
        Mockito.when(customerRepository.save(any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> customerService.updateCustomerDetails(customerId, customerDto));
    }

    private static CustomerDto getCustomerDto() {
        return CustomerDto.builder()
                .customerId(1234)
                .customerName("Tom")
                .customerContact("876543")
                .customerAddress("New York")
                .build();
    }

    private static Customer getCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .customerId(customerDto.getCustomerId())
                .customerName(customerDto.getCustomerName())
                .customerContact(customerDto.getCustomerContact())
                .customerAddress(customerDto.getCustomerAddress())
                .membership(CustomerMembership.REGULAR)
                .build();
    }
}