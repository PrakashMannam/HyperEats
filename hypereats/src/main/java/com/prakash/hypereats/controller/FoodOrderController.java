package com.prakash.hypereats.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prakash.hypereats.dto.AddOrderItemRequest;
import com.prakash.hypereats.dto.FoodOrderResponse;
import com.prakash.hypereats.dto.OrderItemResponse;
import com.prakash.hypereats.service.FoodOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class FoodOrderController {

  private final FoodOrderService foodOrderService;

  public FoodOrderController(FoodOrderService foodOrderService) {
    this.foodOrderService = foodOrderService;
  }

  @PostMapping("/users/{userId}/restaurants/{restaurantId}")
  public FoodOrderResponse createOrder(@PathVariable Long userId, @PathVariable Long restaurantId) {
    return foodOrderService.createOrder(userId, restaurantId);
  }

  @GetMapping
  public List<FoodOrderResponse> getAllOrders() {
    return foodOrderService.getAllOrders();
  }

  @GetMapping("/{id}")
  public FoodOrderResponse getOrderById(@PathVariable Long id) {
    return foodOrderService.getOrderById(id);
  }

  @DeleteMapping("/{id}")
  public String cancelOrderById(@PathVariable Long id) {
    foodOrderService.deleteOrderById(id);
    return "Order cancelled successfully";
  }

  @GetMapping("/{id}/items")
  public List<OrderItemResponse> getOrderItems(@PathVariable Long id) {
    return foodOrderService.getOrderItems(id);
  }

  @PostMapping("/{orderId}/items") // old: "/{orderId}/items/{menuItemId}/quantity/{quantity}"
  public OrderItemResponse addItemToOrder(
      @PathVariable Long orderId,
      @Valid @RequestBody AddOrderItemRequest request) {

    return foodOrderService.addItemToOrder(
        orderId,
        request.getMenuItemId(),
        request.getQuantity());
  }

  @PutMapping("/{id}/accept")
  public FoodOrderResponse acceptOrder(@PathVariable Long id) {
    return foodOrderService.acceptOrder(id);
  }
  
  @PutMapping("/{id}/prepare")
  public FoodOrderResponse markPreparing(@PathVariable Long id) {
    return foodOrderService.markPreparing(id);
  }

  @PutMapping("/{id}/ready")
  public FoodOrderResponse markReady(@PathVariable Long id) {
    return foodOrderService.markReady(id);
  }

  @PutMapping("/{id}/pickup")
  public FoodOrderResponse markPickedUp(@PathVariable Long id) {
    return foodOrderService.markPickedUp(id);
  }

  @PutMapping("/{id}/deliver")
  public FoodOrderResponse markDelivered(@PathVariable Long id) {
    return foodOrderService.markDelivered(id);
  }
}
