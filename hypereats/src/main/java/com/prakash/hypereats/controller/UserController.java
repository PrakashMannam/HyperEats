package com.prakash.hypereats.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prakash.hypereats.entity.User;
import com.prakash.hypereats.service.UserService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/users")
public class UserController {
  
  private UserService userService;
  
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PutMapping
  public User createUser(@Valid @RequestBody User user) {
    return userService.createUser(user);
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }
  
  @PutMapping("/{id}")
  public User updateUser(@PathVariable Long id, @Valid @RequestBody User updatedUser) {
    return userService.updateUser(id, updatedUser);
  }
  
  @DeleteMapping("/{id}")
  public String deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return "User deleted successfully";
  }
}
