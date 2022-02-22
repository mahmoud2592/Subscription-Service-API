package com.subscriptionAPI.repository;

import com.subscriptionAPI.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findById(long id);
    List<Card> findByCustomerId(long id);
}
