package com.subscriptionAPI.controller;

import com.subscriptionAPI.model.Customer;
import com.subscriptionAPI.model.JwtResponse;
import com.subscriptionAPI.service.CustomerService;
import com.subscriptionAPI.utility.JWTUtility;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String home() {
        return "Welcome to Daily Code Buffer!!";
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@ApiParam(value = "JWT is used in this authentication in order to have authorization to access the rest of the APIs with username and password only", required = true)@RequestBody Customer jwtRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = customerService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponse(token);
    }

    @PostMapping("/addCustomer")
    public Customer addCustomer(@ApiParam(value = "created_at, is_deleted, last_login, is_admin and balance they are hidden values which are not mandatory to send")  @RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }
}
