package com.example.onlinelibrary.customers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findCustomers() {
        return ResponseEntity.ok(customerService.findCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CreateCustomerDTO createCustomerDTO) {
        return  ResponseEntity.ok(customerService.createCustomer(createCustomerDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(id);

        return ResponseEntity.ok(customerService.updateCustomer(customerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);

        return ResponseEntity.ok().build();
    }
}
