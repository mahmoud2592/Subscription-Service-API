package com.subscriptionAPI.repository;

import com.subscriptionAPI.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
    Customer findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
