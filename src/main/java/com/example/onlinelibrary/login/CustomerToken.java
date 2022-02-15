package com.example.onlinelibrary.login;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

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
public class CustomerToken {

    @Id
    @GeneratedValue(generator = "CUST_TOKEN_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="CUST_TOKEN_SEQ_GEN", sequenceName = "CUST_TOKEN_SEQ", allocationSize = 1)
    private Long id;

    private Long customerId;

    private String token;
}
