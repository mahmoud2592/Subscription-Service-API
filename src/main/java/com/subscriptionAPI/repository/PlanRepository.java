package com.subscriptionAPI.repository;
import com.subscriptionAPI.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    Plan findPlanById(long id);
    List<Plan> findByCustomerId(long id);
    List<Plan> findProductsByProductId(long id);
}
