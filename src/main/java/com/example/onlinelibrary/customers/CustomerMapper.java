package com.example.onlinelibrary.customers;

public final class CustomerMapper {

    private CustomerMapper() {}

    public static CustomerDTO toCustomerDto(final Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .username(customer.getUsername())
                .build();
    }

    public static Customer toCustomer(final CreateCustomerDTO customerDTO, final String hashedPassword) {
        return Customer.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .username(customerDTO.getUsername())
                .password(hashedPassword)
                .build();
    }

    public static Customer toUpdateCustomer(final CustomerDTO customerDTO, final Customer customerById) {
        customerById.setEmail(customerDTO.getEmail());
        customerById.setName(customerDTO.getName());

        return customerById;
    }
}
