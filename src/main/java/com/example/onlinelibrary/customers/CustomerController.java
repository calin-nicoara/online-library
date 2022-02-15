package com.example.onlinelibrary.customers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerDTOValidator customerDTOValidator;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findCustomers() {
        return ResponseEntity.ok(customerService.findCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CreateCustomerDTO createCustomerDTO,
                                                      BindingResult bindingResult) {
        return validateForCreate(createCustomerDTO, bindingResult)
                .orElseGet(() ->  ResponseEntity.ok(customerService.createCustomer(createCustomerDTO)));
    }

    private Optional<ResponseEntity<?>> validateForCreate(final CreateCustomerDTO customerCommon, final BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return Optional.of(ResponseEntity.badRequest().body(bindingResult.getAllErrors()));
        }

        customerDTOValidator.validate(customerCommon, bindingResult);

        if(bindingResult.hasErrors()) {
            return Optional.of(ResponseEntity.badRequest().body(bindingResult.getAllErrors()));
        }

        return Optional.empty();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") Long id,
                                                      @RequestBody @Valid CustomerDTO customerDTO) {
        customerDTO.setId(id);
        return ResponseEntity.ok(customerService.updateCustomer(customerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);

        return ResponseEntity.ok().build();
    }
}
