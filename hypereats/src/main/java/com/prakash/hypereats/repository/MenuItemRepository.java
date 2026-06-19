package com.prakash.hypereats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prakash.hypereats.entity.MenuItem;


public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
  
  List<MenuItem> findByRestaurantId(Long restaurantId);
}
