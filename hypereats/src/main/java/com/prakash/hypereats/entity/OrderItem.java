package com.prakash.hypereats.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private FoodOrder foodOrder;

  @ManyToOne
  @JoinColumn(name = "menu_item_id")
  private MenuItem menuItem;

  @Min(value = 1 , message = "Quantity must be at least 1")
  private Integer quantity;

  private Double priceAtOrderTime;

}
