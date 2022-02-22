package com.subscriptionAPI.controller;

import com.subscriptionAPI.model.Subscription;
import com.subscriptionAPI.service.SubscriptionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@ApiOperation(value = "This API's has two end-points for either upgrade or cancel Subscription", response = Iterable.class)
@RestController
public class SubscriptionController {
    @Autowired
    SubscriptionService subscriptionService;
    @ApiOperation(value = "This API's upgrade Subscription, start_date, end_date, created_at and is_default are hidden fields which are not mandatory fields", response = Iterable.class)
    @PostMapping("/{plan_id}/upgrade")
    public Subscription upgrade(@ApiParam(value = "flag and plan_id are mandatory in case it first subscription for this plan unless that you have to send the full subscription object except created_at and is_deleted", required = true) @RequestBody Subscription subscription , @PathVariable(value = "plan_id") @ApiParam(value="**mandatory parameter") Long plan_id) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return subscriptionService.upgradeSubscription(subscription, username, plan_id);
    }
    @ApiOperation(value = "This API's is for cancel Subscription plan_id and subscription_id are mandatory fields", response = Iterable.class)
    @DeleteMapping("/{plan_id}/{subscription_id}/cancel")
    public String cancel(@PathVariable(value = "plan_id") Long plan_id, @PathVariable(value = "subscription_id") @ApiParam(value="**mandatory parameter") Long subscription_id) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return subscriptionService.cancelSubscription(subscription_id, username);
    }
}
