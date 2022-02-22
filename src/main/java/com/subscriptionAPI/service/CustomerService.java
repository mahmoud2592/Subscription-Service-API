package com.subscriptionAPI.service;

import com.subscriptionAPI.model.Customer;
import com.subscriptionAPI.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
@Service
public class CustomerService implements UserDetailsService {
    @Autowired
    private CustomerRepository repository;

    public Customer saveCustomer(Customer customer){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        customer.setCreated_at(timestamp);
        return repository.saveAndFlush(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return new User(userName,repository.findByUsername(userName).getPassword(),new ArrayList<>());
    }

}
