package com.prakash.hypereats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemResponse {
  private Long id;
  private Long menuItemId;
  private String menuItemName;
  private Integer quantity;
  private Double priceAtOrderTime;
  private Double totalPrice;
  
}