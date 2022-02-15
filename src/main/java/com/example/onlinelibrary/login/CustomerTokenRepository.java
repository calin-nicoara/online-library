package com.example.onlinelibrary.login;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTokenRepository  extends JpaRepository<CustomerToken, Long> {
}
