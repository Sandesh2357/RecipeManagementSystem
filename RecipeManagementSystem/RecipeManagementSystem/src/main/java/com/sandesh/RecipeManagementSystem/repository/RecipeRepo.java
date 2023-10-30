package com.sandesh.RecipeManagementSystem.repository;

import com.sandesh.RecipeManagementSystem.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepo extends JpaRepository<Recipe,Long> {

    Recipe findFirstByRecipeName(String name);
}
