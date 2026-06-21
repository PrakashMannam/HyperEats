package com.prakash.hypereats.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prakash.hypereats.entity.FoodOrder;
import com.prakash.hypereats.entity.OrderStatus;
import com.prakash.hypereats.entity.Restaurant;
import com.prakash.hypereats.exception.ResourceNotFoundException;
import com.prakash.hypereats.repository.FoodOrderRepository;
import com.prakash.hypereats.repository.RestaurantRepository;
import com.prakash.hypereats.repository.UserRepository;
import com.prakash.hypereats.entity.User;

@Service
public class FoodOrderService {

  private final FoodOrderRepository foodOrderRepository;
  private final UserRepository userRepository;
  private final RestaurantRepository restaurantRepository;

  public FoodOrderService(
      FoodOrderRepository foodOrderRepository,
      UserRepository userRepository,
      RestaurantRepository restaurantRepository) {

    this.foodOrderRepository = foodOrderRepository;
    this.userRepository = userRepository;
    this.restaurantRepository = restaurantRepository;
  }

  public FoodOrder createOrder(Long userId, Long restaurantId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

    FoodOrder order = new FoodOrder();
    order.setUser(user);
    order.setRestaurant(restaurant);
    order.setStatus(OrderStatus.PENDING);

    return foodOrderRepository.save(order);
  }

  public List<FoodOrder> getAllOrders() {
    return foodOrderRepository.findAll();
  }

  public FoodOrder getOrderById(Long id) {
    return foodOrderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order with id: " + id + " not found"));
  }

  public void deleteOrderById(Long id) {
    FoodOrder order = foodOrderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order with id: " + id + " not found"));

    foodOrderRepository.delete(order);
  }
}
