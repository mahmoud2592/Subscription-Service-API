package com.subscriptionAPI.repository;

import com.subscriptionAPI.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCustomerId(long id);
    Product findProductById(long id);
}
