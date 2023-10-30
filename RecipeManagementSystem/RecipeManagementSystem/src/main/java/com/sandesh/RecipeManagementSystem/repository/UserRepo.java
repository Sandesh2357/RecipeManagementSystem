package com.sandesh.RecipeManagementSystem.repository;

import com.sandesh.RecipeManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findFirstByUserEmail(String newEmail);
}
