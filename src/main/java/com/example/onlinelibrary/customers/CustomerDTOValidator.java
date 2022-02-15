package com.example.onlinelibrary.customers;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CustomerDTOValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return CreateCustomerDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        CreateCustomerDTO customerCommon = (CreateCustomerDTO) target;

        if(customerCommon.getEmail().equals(customerCommon.getUsername())) {
            errors.rejectValue("email", "EmailEqualToUsername", "The email needs to be different than the username!");
        }
    }
}
