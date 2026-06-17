package com.prakash.hypereats.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prakash.hypereats.entity.Restaurant;
import com.prakash.hypereats.exception.ResourceNotFoundException;
import com.prakash.hypereats.repository.RestaurantRepository;

@Service
public class RestaurantService {
  private final RestaurantRepository restaurantRepository;

  public RestaurantService(RestaurantRepository restaurantRepository) {
    this.restaurantRepository = restaurantRepository;
  }

  public List<Restaurant> getAllRestaurants() {
    return restaurantRepository.findAll();
  }

  public Restaurant getRestaurantById(Long id) {
    return restaurantRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
  }
  
  public Restaurant addRestaurant(Restaurant restaurant) {
    return restaurantRepository.save(restaurant);
  }

  public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {
    Restaurant restaurant = restaurantRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));

    restaurant.setName(updatedRestaurant.getName());
    restaurant.setAddress(updatedRestaurant.getAddress());

    return restaurantRepository.save(restaurant);
  }

  public void deleteRestaurant(Long id) {
    Restaurant restaurant = restaurantRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));

    restaurantRepository.delete(restaurant);
  }
}
