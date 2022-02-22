package com.subscriptionAPI.service;

import com.subscriptionAPI.exception.ResourceNotFoundException;
import com.subscriptionAPI.model.Plan;
import com.subscriptionAPI.model.Product;
import com.subscriptionAPI.repository.CustomerRepository;
import com.subscriptionAPI.repository.PlanRepository;
import com.subscriptionAPI.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class PlanService {
    @Autowired
    private PlanRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public Plan savePlan(Plan plan, String username, Long product_id){

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        plan.setCustomer(customerRepository.findByUsername(username));
        Product product = productRepository.findProductById(product_id);
        if(product.getCustomer()!=plan.getCustomer()){
            throw new ResourceNotFoundException("This customer is not authorized to create a plan associated with this product");
        }
        plan.setProduct(product);
        plan.setCreated_at(timestamp);
        List<Plan> plans = repository.findProductsByProductId(plan.getProduct().getId());
        int n = plans.size();
        if(n>=3){
            throw new ResourceNotFoundException("You must have fixed number of plans which is three");
        }else if(n==2){
            plan.setIs_active(true);
            Plan is_saved_plan = repository.saveAndFlush(plan);
            if(is_saved_plan!=null){
                for(int i=0;i<plans.size();i++){
                    plans.get(i).setIs_active(true);
                }
                repository.saveAll(plans);
                return is_saved_plan;
            }
        }
        return repository.saveAndFlush(plan);
    }

    public List<Plan> getPlansByUserId(String  username){
        return repository.findByCustomerId(customerRepository.findByUsername(username).getId());
    }

    public Plan getPlanById(Long id){
        return repository.findById(id).orElse(null);
    }

    public String DeletePlanById(Long id){
        repository.deleteById(id);
        return "Deleted Card Id: " + id;
    }
}
