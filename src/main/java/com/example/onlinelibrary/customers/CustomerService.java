package com.example.onlinelibrary.customers;

import com.example.onlinelibrary.exceptions.NotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<CustomerDTO> findCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::toCustomerDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerDTO findCustomerById(final Long id) {
        return customerRepository.findById(id).map(CustomerMapper::toCustomerDto)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));
    }

    public CustomerDTO createCustomer(final CreateCustomerDTO createCustomerDTO) {
        String hashedPassword = BCrypt.withDefaults().hashToString(12, createCustomerDTO.getPassword().toCharArray());
        Customer customer = CustomerMapper.toCustomer(createCustomerDTO, hashedPassword);
        Customer savedCustomer = customerRepository.save(customer);

        return CustomerMapper.toCustomerDto(savedCustomer);
    }

    public CustomerDTO updateCustomer(final CustomerDTO customerDTO) {
        if(!customerRepository.existsById(customerDTO.getId())) {
            throw new NotFoundException("Customer not found with id: " + customerDTO.getId());
        }

        Customer customer = CustomerMapper.toCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);

        return CustomerMapper.toCustomerDto(savedCustomer);
    }

    public void deleteCustomer(final Long id) {
        customerRepository.deleteById(id);
    }
}
