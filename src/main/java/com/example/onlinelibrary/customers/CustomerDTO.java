package com.example.onlinelibrary.customers;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
