package com.prakash.hypereats.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prakash.hypereats.entity.MenuItem;
import com.prakash.hypereats.entity.Restaurant;
import com.prakash.hypereats.exception.ResourceNotFoundException;
import com.prakash.hypereats.repository.MenuItemRepository;
import com.prakash.hypereats.repository.RestaurantRepository;

@Service
public class MenuItemService {
  
  private final MenuItemRepository menuItemRepository;
  private final RestaurantRepository restaurantRepository;

  public MenuItemService(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
    this.menuItemRepository = menuItemRepository;
    this.restaurantRepository = restaurantRepository;
  }

  public MenuItem addMenuItem(Long restaurantId, MenuItem menuItem) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

    menuItem.setRestaurant(restaurant);

    return menuItemRepository.save(menuItem);
  }
  
  public List<MenuItem> getMenuItemsByRestaurant(Long restaurantId) {
    return menuItemRepository.findByRestaurantId(restaurantId);
  }
}
