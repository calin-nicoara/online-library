package com.example.onlinelibrary.customers;

public final class CustomerMapper {

    private CustomerMapper() {}

    public static CustomerDTO toCustomerDto(final Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
    }

    public static Customer toCustomer(final CustomerDTO customerDTO) {
        return Customer.builder()
                .id(customerDTO.getId())
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .build();
    }

    public static Customer toCustomer(final CreateCustomerDTO customerDTO, final String hashedPassword) {
        return Customer.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .password(hashedPassword)
                .build();
    }
}
