package com.subscriptionAPI.service;

import com.subscriptionAPI.model.Invoice;
import com.subscriptionAPI.repository.CustomerRepository;
import com.subscriptionAPI.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    public Invoice getByInvoiceId(Long id){
        return repository.findInvoiceById(id);
    }

    public List<Invoice> getInvoicesByCustomerId(String username){
        return repository.findByCustomerId(customerRepository.findByUsername(username).getId());
    }
}
