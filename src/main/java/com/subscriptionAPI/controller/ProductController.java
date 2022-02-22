package com.subscriptionAPI.controller;

import com.subscriptionAPI.model.Product;
import com.subscriptionAPI.service.ProductService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/addProduct")
    public Product addProduct(@ApiParam(value = "please note that created_at and is_deleted because they are hidden values which are not mandatory to send", required = true) @RequestBody Product product) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return productService.saveCard(product, username);
    }

    @GetMapping("/products")
    public List<Product> findAllPlans() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return productService.getProductsByUsername(username);
    }

    @GetMapping("/product/{id}")
    public Product findInvoiceById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
}
