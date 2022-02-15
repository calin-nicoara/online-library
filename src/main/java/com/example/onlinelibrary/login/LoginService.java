package com.example.onlinelibrary.login;

import com.example.onlinelibrary.customers.Customer;
import com.example.onlinelibrary.customers.CustomerRepository;

import org.springframework.stereotype.Service;

import java.util.UUID;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final CustomerRepository customerRepository;
    private final CustomerTokenRepository customerTokenRepository;

    public String createAndGetToken(final LoginDTO loginDTO) {
        Customer customer = customerRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username or password not correct!"));

        if(!verifyPassword(customer.getPassword(), loginDTO.getPassword())) {
            throw new IllegalArgumentException("Username or password not correct!");
        }

        // Would use JWT in production
        String token = UUID.randomUUID().toString();

        CustomerToken customerToken = CustomerToken.builder()
                .customerId(customer.getId())
                .token(token)
                .build();

        customerTokenRepository.save(customerToken);

        return token;
    }

    private boolean verifyPassword(final String passwordHash, final String passwordToValidate) {
        BCrypt.Result result = BCrypt.verifyer().verify(passwordToValidate.toCharArray(), passwordHash);

        return result.verified;
    }


}
