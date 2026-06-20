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

  @GetMapping("/{id}")
  public MenuItem getMenuItemById(@PathVariable Long restaurantId, @PathVariable Long id) {
    return menuItemService.getMenuItemById(restaurantId, id);
  }

  @PutMapping("/{id}")
  public MenuItem updateMenuItem(
      @PathVariable Long restaurantId,
      @PathVariable Long id,
      @Valid @RequestBody MenuItem updatedMenuItem) {
    return menuItemService.updateMenuItem(restaurantId, id, updatedMenuItem);
  }

  @DeleteMapping("/{id}")
  public String deleteMenuItem(@PathVariable Long restaurantId, @PathVariable Long id) {
    menuItemService.deleteMenuItem(restaurantId, id);

    return "Menu item deleted successfully";
  }
}
