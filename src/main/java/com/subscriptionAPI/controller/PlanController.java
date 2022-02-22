package com.subscriptionAPI.controller;

import com.subscriptionAPI.model.Plan;
import com.subscriptionAPI.service.PlanService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class PlanController {
    @Autowired
    PlanService planService;

    @PostMapping("/{product_id}/addPlan")
    public Plan addPlan(@ApiParam(value = "created_at, is_deleted and is_active they are hidden values which are not mandatory to send") @RequestBody Plan plan, @PathVariable(value = "product_id") Long product_id) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return planService.savePlan(plan, username, product_id);
    }

    @GetMapping("/plans")
    public List<Plan> findAllPlans() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return planService.getPlansByUserId(username);
    }
    @GetMapping("/plan/{id}")
    public Plan findInvoiceById(@PathVariable Long id) {
        return planService.getPlanById(id);
    }
}
