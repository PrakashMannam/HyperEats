package com.prakash.hypereats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prakash.hypereats.entity.FoodOrder;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
}
