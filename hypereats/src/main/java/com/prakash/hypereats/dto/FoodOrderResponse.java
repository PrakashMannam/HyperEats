package com.prakash.hypereats.dto;

import com.prakash.hypereats.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodOrderResponse {
  
  private Long id;
  private Long userId;
  private String userName;
  private Long restaurantId;
  private String restaurantName;
  private OrderStatus status;
  
}
