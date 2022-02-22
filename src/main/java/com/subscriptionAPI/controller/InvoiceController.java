package com.subscriptionAPI.controller;

import com.subscriptionAPI.model.Invoice;
import com.subscriptionAPI.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;
    @GetMapping("/invoices")
    public List<Invoice> findAllInvoices() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return invoiceService.getInvoicesByCustomerId(username);
    }
    @GetMapping("/invoice/{id}")
    public Invoice findInvoiceById(@PathVariable Long id) {
        return invoiceService.getByInvoiceId(id);
    }
}
