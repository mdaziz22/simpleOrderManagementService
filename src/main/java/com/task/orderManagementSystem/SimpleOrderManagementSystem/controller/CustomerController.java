package com.task.orderManagementSystem.SimpleOrderManagementSystem.controller;

import com.task.orderManagementSystem.SimpleOrderManagementSystem.dto.CustomerDto;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.entity.Customer;
import com.task.orderManagementSystem.SimpleOrderManagementSystem.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /*
     * Create a new customer based on the info provided by the user
     */
    @PostMapping("/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDto customerDto) {
        try {
            return new ResponseEntity<>(customerService.createCustomer(customerDto), HttpStatus.OK);
        } catch (Exception ex) {
            ex.getStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Get all customers
     */
    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.getStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Get all customers for a given customerId provided by user
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getCustomerByCustomerId(@PathVariable long customerId) {
        try {
            return new ResponseEntity<>(customerService.getCustomerByCustomerId(customerId), HttpStatus.OK);
        } catch (Exception ex) {
            ex.getStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Delete customer and orders associated for a given customerId provided by user
     */
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable long customerId) {
        try {
            return new ResponseEntity<>(customerService.deleteCustomer(customerId), HttpStatus.OK);
        } catch (Exception ex) {
            ex.getStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Update customer details provided by user
     */
    @PutMapping("/customer/{orderId}")
    public ResponseEntity<Customer> updateCustomerDetails(@PathVariable long customerId, @RequestBody CustomerDto customerDto) {
        try {
            return new ResponseEntity<>(customerService.updateCustomerDetails(customerId, customerDto), HttpStatus.OK);
        } catch (Exception ex) {
            ex.getStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
