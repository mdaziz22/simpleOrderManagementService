package com.task.orderManagementSystem.SimpleOrderManagementSystem.controller;

import com.task.orderManagementSystem.SimpleOrderManagementSystem.dto.CustomerDto;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.Customer;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.CustomerMembership;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.services.CustomerService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void createCustomerSuccessTest() {
        CustomerDto customerDto = getCustomerDto();
        Customer customer = getCustomer(customerDto);
        ResponseEntity<Customer> expectedOutput = new ResponseEntity<>(customer, HttpStatus.OK);
        Mockito.when(customerService.createCustomer(customerDto))
                .thenReturn(expectedOutput.getBody());
        ResponseEntity<Customer> actualOutput = customerController.createCustomer(customerDto);
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void createCustomerFailureTest() {
        CustomerDto customerDto = getCustomerDto();
        ResponseEntity<Customer> expectedOutput = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Mockito.when(customerService.createCustomer(customerDto))
                .thenThrow(new RuntimeException());
        ResponseEntity<Customer> actualOutput = customerController.createCustomer(customerDto);
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void getAllCustomersSuccessTest() {
        CustomerDto customerDto = getCustomerDto();
        List<Customer> customers = new ArrayList<>();
        customers.add(getCustomer(customerDto));
        ResponseEntity<List<Customer>> expectedOutput = new ResponseEntity<>(customers, HttpStatus.OK);
        Mockito.when(customerService.getAllCustomers()).thenReturn(customers);
        ResponseEntity<List<Customer>> actualOutput = customerController.getAllCustomers();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getAllCustomersFailure() {
        ResponseEntity<List<Customer>> expectedOutput = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Mockito.when(customerService.getAllCustomers()).thenThrow(new RuntimeException());
        ResponseEntity<List<Customer>> actualOutput = customerController.getAllCustomers();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getCustomerByCustomerIdSuccessTest() {
        CustomerDto customerDto = getCustomerDto();
        Customer customer = getCustomer(customerDto);
        ResponseEntity<Customer> expectedOutput = new ResponseEntity<>(customer, HttpStatus.OK);
        Mockito.when(customerService.getCustomerByCustomerId(customerDto.getCustomerId())).thenReturn(customer);
        ResponseEntity<Customer> actualOutput = customerController.getCustomerByCustomerId(customerDto.getCustomerId());
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getCustomerByCustomerIdFailureTest() {
        long customerId = 1235;
        ResponseEntity<Customer> expectedOutput = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Mockito.when(customerService.getCustomerByCustomerId(customerId)).thenThrow(new RuntimeException());
        ResponseEntity<Customer> actualOutput = customerController.getCustomerByCustomerId(customerId);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void deleteCustomerSuccessTest() {
        long customerId = 1234;
        String response = "Customer with customer id - " + customerId + " deleted successfully";
        ResponseEntity<String> expectedOutput = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(customerService.deleteCustomer(customerId)).thenReturn(response);
        ResponseEntity<String> actualOutput = customerController.deleteCustomer(customerId);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void deleteCustomerFailureTest() {
        long customerId = 1235;
        ResponseEntity<String> expectedOutput = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Mockito.when(customerService.deleteCustomer(customerId)).thenThrow(new RuntimeException());
        ResponseEntity<String> actualOutput = customerController.deleteCustomer(customerId);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void updateCustomerDetailsSuccessTest() {
        long customerId = 1234;
        CustomerDto customerDto = getCustomerDto();
        Customer customer = getCustomer(customerDto);
        ResponseEntity<Customer> expectedOutput = new ResponseEntity<>(customer, HttpStatus.OK);
        Mockito.when(customerService.updateCustomerDetails(customerId, customerDto)).thenReturn(customer);
        ResponseEntity<Customer> actualOutput = customerController.updateCustomerDetails(customerId, customerDto);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void updateCustomerDetailsFailureTest() {
        long customerId = 1234;
        CustomerDto customerDto = getCustomerDto();
        ResponseEntity<Customer> expectedOutput = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Mockito.when(customerService.updateCustomerDetails(customerId, customerDto)).thenThrow(new RuntimeException());
        ResponseEntity<Customer> actualOutput = customerController.updateCustomerDetails(customerId, customerDto);
        assertEquals(expectedOutput, actualOutput);
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