package com.prakash.hypereats.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prakash.hypereats.entity.MenuItem;


public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
  
  List<MenuItem> findByRestaurantId(Long restaurantId);

  Optional<MenuItem> findByIdAndRestaurantId(Long id, Long restaurantId);
}
