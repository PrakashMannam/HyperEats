package com.prakash.hypereats.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prakash.hypereats.entity.FoodOrder;
import com.prakash.hypereats.entity.OrderItem;
import com.prakash.hypereats.entity.OrderStatus;
import com.prakash.hypereats.entity.Restaurant;
import com.prakash.hypereats.exception.ResourceNotFoundException;
import com.prakash.hypereats.repository.FoodOrderRepository;
import com.prakash.hypereats.repository.RestaurantRepository;
import com.prakash.hypereats.repository.UserRepository;
import com.prakash.hypereats.entity.User;
import com.prakash.hypereats.repository.OrderItemRepository;
import com.prakash.hypereats.entity.MenuItem;
import com.prakash.hypereats.repository.MenuItemRepository;

@Service
public class FoodOrderService {

  private final FoodOrderRepository foodOrderRepository;
  private final UserRepository userRepository;
  private final RestaurantRepository restaurantRepository;
  private final OrderItemRepository orderItemRepository;
  private final MenuItemRepository menuItemRepository;

  public FoodOrderService(
      FoodOrderRepository foodOrderRepository,
      UserRepository userRepository,
      RestaurantRepository restaurantRepository,
      OrderItemRepository orderItemRepository,
      MenuItemRepository menuItemRepository) {

    this.foodOrderRepository = foodOrderRepository;
    this.userRepository = userRepository;
    this.restaurantRepository = restaurantRepository;
    this.orderItemRepository = orderItemRepository;
    this.menuItemRepository = menuItemRepository;
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

  public List<OrderItem> getOrderItems(Long orderId) {
    FoodOrder order = foodOrderRepository.findById(orderId)
        .orElseThrow(() -> new ResourceNotFoundException("Order with id: " + orderId + " not found"));

    return orderItemRepository.findByFoodOrderId(order.getId());
  }

  public OrderItem addItemToOrder(Long orderId, Long menuItemId, Integer quantity) {
    

    if (quantity < 1) {
      throw new IllegalArgumentException("Quantity must be at least 1");
    }
    FoodOrder order = foodOrderRepository.findById(orderId)
        .orElseThrow(() -> new ResourceNotFoundException("Order with id: " + orderId + " not found"));

    MenuItem menuItem = menuItemRepository.findById(menuItemId)
        .orElseThrow(() -> new ResourceNotFoundException("Menu Item not found with id: " + menuItemId));

    if (!menuItem.getRestaurant().getId().equals(order.getRestaurant().getId())) {
      throw new IllegalArgumentException("Menu item does not belong to the order restaurant");
    }
    OrderItem orderItem = new OrderItem();
    orderItem.setFoodOrder(order);
    orderItem.setMenuItem(menuItem);
    orderItem.setQuantity(quantity);
    orderItem.setPriceAtOrderTime(menuItem.getPrice());

    return orderItemRepository.save(orderItem);
  }
}
