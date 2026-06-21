package com.prakash.hypereats.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prakash.hypereats.entity.User;
import com.prakash.hypereats.exception.ResourceNotFoundException;
import com.prakash.hypereats.repository.UserRepository;

@Service
public class UserService {
  
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " is not found"));
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }

  public User updateUser(Long id, User updatedUser) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " is not found"));

    user.setName(updatedUser.getName());
    user.setEmail(updatedUser.getEmail());
    user.setPhone(updatedUser.getPhone());

    return userRepository.save(user);
  }
  
  public void deleteUser(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " is not found"));

    

    userRepository.delete(user);
  }
}
