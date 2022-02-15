package com.example.onlinelibrary.customers;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {

    private Long id;

    private String username;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;
}
