package com.subscriptionAPI.repository;

import com.subscriptionAPI.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByCustomerIdAndPlanId(long customer_id, long plan_id);
    List<Subscription> findByCustomerId(long id);
    List<Subscription> findByPlanId(long id);
    @Query(value = "select * from subscriptions inner join plans on subscriptions.plan_id = plans.id inner join product on plans.product_id = product.id where products.id = :key_id;", nativeQuery = true)
    List<Subscription> findByProductId(@Param("key_id") long id);
    Subscription findSubscriptionById(long id);
}
