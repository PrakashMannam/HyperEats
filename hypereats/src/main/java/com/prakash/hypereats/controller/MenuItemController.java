package com.prakash.hypereats.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prakash.hypereats.entity.MenuItem;
import com.prakash.hypereats.service.MenuItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurants/{restaurantId}/menu-items")
public class MenuItemController {
  
  private MenuItemService menuItemService;

  public MenuItemController(MenuItemService menuItemService) {
    this.menuItemService = menuItemService;
  }

  @PostMapping
    public MenuItem addMenuItem(@PathVariable Long restaurantId, @Valid @RequestBody MenuItem menuItem) {
        return menuItemService.addMenuItem(restaurantId, menuItem);
    }

    @GetMapping
    public List<MenuItem> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        return menuItemService.getMenuItemsByRestaurant(restaurantId);
    }
}
