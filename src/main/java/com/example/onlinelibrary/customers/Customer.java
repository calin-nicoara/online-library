package com.example.onlinelibrary.customers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer {

    @Id
    @GeneratedValue(generator = "CUST_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="CUST_SEQ_GEN", sequenceName = "CUST_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    private String password;
}
