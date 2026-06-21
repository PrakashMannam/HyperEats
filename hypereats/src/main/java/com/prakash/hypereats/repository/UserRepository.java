package com.prakash.hypereats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prakash.hypereats.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  
}
