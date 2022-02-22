package com.subscriptionAPI.service;

import com.subscriptionAPI.model.Product;
import com.subscriptionAPI.repository.CustomerRepository;
import com.subscriptionAPI.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    public Product saveCard(Product product, String username){
        List<Product> products = repository.findByCustomerId(customerRepository.findByUsername(username).getId());

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        product.setCustomer(customerRepository.findByUsername(username));
        product.setCreated_at(timestamp);
        return repository.saveAndFlush(product);
    }

    public List<Product> getProductsByUsername(String username){
        return repository.findByCustomerId(customerRepository.findByUsername(username).getId());
    }

    public Product getProductById(Long id){
        return repository.findById(id).orElse(null);
    }

    public String DeleteProductById(Long id){
        repository.deleteById(id);
        return "Deleted Card Id: " + id;
    }
}
