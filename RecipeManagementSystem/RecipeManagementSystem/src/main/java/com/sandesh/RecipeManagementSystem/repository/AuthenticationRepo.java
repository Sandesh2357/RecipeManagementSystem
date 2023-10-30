package com.sandesh.RecipeManagementSystem.repository;

import com.sandesh.RecipeManagementSystem.model.AuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepo extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findFirstByTokenValue(String tokenValue);
}
