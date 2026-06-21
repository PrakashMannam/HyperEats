package com.prakash.hypereats.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prakash.hypereats.entity.FoodOrder;
import com.prakash.hypereats.service.FoodOrderService;

@RestController
@RequestMapping("/orders")
public class FoodOrderController {
  
  private final FoodOrderService foodOrderService;

  public FoodOrderController(FoodOrderService foodOrderService) {
    this.foodOrderService = foodOrderService;
  }
    
  @PostMapping("/users/{userId}/restaurants/{restaurantId}")
  public FoodOrder createOrder(@PathVariable Long userId, @PathVariable Long restaurantId) {
    return foodOrderService.createOrder(userId, restaurantId);
  }

  @GetMapping
  public List<FoodOrder> getAllOrders() {
    return foodOrderService.getAllOrders();
  }

  @GetMapping("/{id}")
  public FoodOrder getOrderById(@PathVariable Long id) {
    return foodOrderService.getOrderById(id);
  }

  @DeleteMapping("/{id}")
  public String cancelOrderById(@PathVariable Long id) {
    foodOrderService.deleteOrderById(id);
    return "Order cancelled successfully";
  }
}
