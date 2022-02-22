package com.subscriptionAPI.service;

import com.subscriptionAPI.exception.ResourceNotFoundException;
import com.subscriptionAPI.model.*;
import com.subscriptionAPI.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository repository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Subscription upgradeSubscription(Subscription subscription, String username, Long plan_id){
            Customer current_customer = customerRepository.findByUsername(username);
            Subscription current_subscription = repository.findByCustomerIdAndPlanId(current_customer.getId(), plan_id);
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            Plan plan = planRepository.findPlanById(plan_id);
            Invoice invoice = new Invoice();
            boolean sign = false;
            if(subscription.getFlag()==SubscriptionStatusEnum.flag.NONE.ordinal()){
                throw new ResourceNotFoundException("In order to be subscribe you should choose a plan either Monthly or Yearly" );
            }else if(plan.isIs_active()==false){
                throw new ResourceNotFoundException("Sorry this plan is not yet activate" );
            }else {
                if (current_subscription==null){
                    subscription.setStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
                    if(subscription.getFlag()==SubscriptionStatusEnum.flag.MONTHLY.ordinal()){
                        subscription.setEnd_date(Timestamp.valueOf(LocalDate.now().plusMonths(1).atStartOfDay()));
                    }else if(subscription.getFlag()==SubscriptionStatusEnum.flag.YEARLY.ordinal()){
                        subscription.setEnd_date(Timestamp.valueOf(LocalDate.now().plusYears(1).atStartOfDay()));
                    }
                    subscription.setCustomer(current_customer);
                    subscription.setPlan(plan);
                    subscription.setCreated_at(timestamp);
                    return repository.saveAndFlush(subscription);
                }
                if(current_subscription.getCustomer()!=current_customer){
                    throw new ResourceNotFoundException("You don't have authorization for this action" );
                }
                current_subscription.setFlag(subscription.getFlag());
                Timestamp end_date = timestamp;
                if(current_subscription.getEnd_date().before(timestamp)){
                    end_date = current_subscription.getEnd_date();
                }
                long daysBetween = Duration.between(current_subscription.getStartDate().toLocalDateTime(), end_date.toLocalDateTime()).toDays();
                Double planPrice = plan.getPrice_per_month();
                if(planPrice!=0 && daysBetween!=0){
                    sign = true;
                    Double payValue = planPrice/daysBetween;
                    invoice.setAmount(payValue);
                    invoice.setCustomer(current_customer);
                    invoice.setCreated_at(timestamp);
                }

                if(subscription.getFlag()==SubscriptionStatusEnum.flag.MONTHLY.ordinal()){
                    current_subscription.setStartDate(timestamp);
                    current_subscription.setEnd_date(Timestamp.valueOf(LocalDate.now().plusMonths(1).atStartOfDay()));
                }else if(subscription.getFlag()==SubscriptionStatusEnum.flag.YEARLY.ordinal()){
                    current_subscription.setStartDate(timestamp);
                    current_subscription.setEnd_date(Timestamp.valueOf(LocalDate.now().plusYears(1).atStartOfDay()));
                }
            }

            Subscription is_saved_subscription = repository.saveAndFlush(current_subscription);
            if(is_saved_subscription!=null && sign==true){
                invoiceRepository.saveAndFlush(invoice);
            }
            return is_saved_subscription;
        }

        public String cancelSubscription(long subscription_id, String username){
            Customer current_customer = customerRepository.findByUsername(username);
            Subscription current_subscription = repository.findSubscriptionById(subscription_id);
            if(current_subscription.getCustomer()!=current_customer){
                throw new ResourceNotFoundException("You don't have authorization for this action" );
            }
            Plan plan = planRepository.findPlanById(current_subscription.getPlan().getId());
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            Timestamp end_date = timestamp;
            if(current_subscription.getEnd_date().before(timestamp)){
                end_date = current_subscription.getEnd_date();
            }
            long daysBetween = Duration.between(current_subscription.getStartDate().toLocalDateTime(), end_date.toLocalDateTime()).toDays();
            Double planPrice = plan.getPrice_per_month();
            boolean sign = false;
            if(planPrice!=0 && daysBetween!=0){
                sign = true;
                Invoice invoice = new Invoice();
                Double payValue = planPrice/daysBetween;
                invoice.setAmount(payValue);
                invoice.setCustomer(current_customer);
                invoice.setCreated_at(timestamp);
                Invoice is_saved_invoice = invoiceRepository.saveAndFlush(invoice);
            }
            repository.deleteById(subscription_id);
            return "Subscription with Id: " + subscription_id + " is Canceled";
        }

        public List<Subscription> getSubscriptionsByPlanId(Long id){
            return repository.findByPlanId(id);
        }

        public List<Subscription> getSubscriptionsByProductId(Long id){
            return repository.findByProductId(id);
        }

        public List<Subscription> getSubscriptionsByCustomerId(Long id){
            return repository.findByCustomerId(id);
        }

        public String DeletePlanById(Long id){
            repository.deleteById(id);
            return "Deleted Card Id: " + id;
        }
    }
