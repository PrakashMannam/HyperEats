package com.prakash.hypereats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prakash.hypereats.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem , Long>{

  // Returns all items associated with the given FoodOrder ID.
// Equivalent to: SELECT * FROM order_item WHERE order_id = ?
  List<OrderItem> findByFoodOrderId(Long foodOrderId);
}
