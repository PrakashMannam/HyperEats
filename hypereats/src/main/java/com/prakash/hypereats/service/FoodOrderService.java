package com.prakash.hypereats.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prakash.hypereats.dto.FoodOrderResponse;
import com.prakash.hypereats.dto.OrderItemResponse;
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

  public FoodOrderResponse createOrder(Long userId, Long restaurantId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

    FoodOrder order = new FoodOrder();
    order.setUser(user);
    order.setRestaurant(restaurant);
    order.setStatus(OrderStatus.PENDING);

    FoodOrder savedOrder = foodOrderRepository.save(order);
    return mapToFoodOrderResponse(savedOrder);
  }

  public List<FoodOrderResponse> getAllOrders() {
    return foodOrderRepository.findAll()
        .stream()
        .map(this::mapToFoodOrderResponse)
        .toList();
  }

  public FoodOrderResponse getOrderById(Long id) {
    FoodOrder order = foodOrderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order with id: " + id + " not found"));

    return mapToFoodOrderResponse(order);
  }

  public void deleteOrderById(Long id) {
    FoodOrder order = foodOrderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order with id: " + id + " not found"));

    foodOrderRepository.delete(order);
  }

  public List<OrderItemResponse> getOrderItems(Long orderId) {
    FoodOrder order = foodOrderRepository.findById(orderId)
        .orElseThrow(() -> new ResourceNotFoundException("Order with id: " + orderId + " not found"));

    return orderItemRepository.findByFoodOrderId(order.getId())
        .stream()
        .map(this::mapToOrderItemResponse)
        .toList();
  }

  public OrderItemResponse addItemToOrder(Long orderId, Long menuItemId, Integer quantity) {

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

    OrderItem savedOrderItem = orderItemRepository.save(orderItem);
    return mapToOrderItemResponse(savedOrderItem);
  }

  public FoodOrderResponse updateOrderStatus(Long id, OrderStatus status) {
    FoodOrder order = foodOrderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order with id: " + id + " not found"));

    order.setStatus(status);

    FoodOrder updatedOrder = foodOrderRepository.save(order);
    
    return mapToFoodOrderResponse(updatedOrder);
  }

  private OrderItemResponse mapToOrderItemResponse(OrderItem orderItem) {
    Double totalPrice = orderItem.getPriceAtOrderTime() * orderItem.getQuantity();

    return new OrderItemResponse(
        orderItem.getId(),
        orderItem.getMenuItem().getId(),
        orderItem.getMenuItem().getName(),
        orderItem.getQuantity(),
        orderItem.getPriceAtOrderTime(),
        totalPrice);
  }

  private FoodOrderResponse mapToFoodOrderResponse(FoodOrder order) {
    return new FoodOrderResponse(
        order.getId(),
        order.getUser().getId(),
        order.getUser().getName(),
        order.getRestaurant().getId(),
        order.getRestaurant().getName(),
        order.getStatus());
  }

  public FoodOrder getExistingOrder(Long id) {
    return foodOrderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order with id: \" + id + \" not found"));
  }

  public FoodOrderResponse acceptOrder(Long id) {
    FoodOrder order = getExistingOrder(id);

    order.setStatus(OrderStatus.ACCEPTED);
    return mapToFoodOrderResponse(foodOrderRepository.save(order));
  }

  public FoodOrderResponse markPreparing(Long id) {
    FoodOrder order = getExistingOrder(id);
    order.setStatus(OrderStatus.PREPARING);
    return mapToFoodOrderResponse(foodOrderRepository.save(order));
  }

  public FoodOrderResponse markReady(Long id) {
    FoodOrder order = getExistingOrder(id);
    order.setStatus(OrderStatus.READY);
    return mapToFoodOrderResponse(foodOrderRepository.save(order));
  }

  public FoodOrderResponse markPickedUp(Long id) {
    FoodOrder order = getExistingOrder(id);
    order.setStatus(OrderStatus.PICKED_UP);
    return mapToFoodOrderResponse(foodOrderRepository.save(order));
  }

  public FoodOrderResponse markDelivered(Long id) {
    FoodOrder order = getExistingOrder(id);
    order.setStatus(OrderStatus.DELIVERED);
    return mapToFoodOrderResponse(foodOrderRepository.save(order));
  }
}
