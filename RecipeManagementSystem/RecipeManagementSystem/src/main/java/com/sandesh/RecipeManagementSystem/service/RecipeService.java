package com.sandesh.RecipeManagementSystem.service;

import com.sandesh.RecipeManagementSystem.model.Recipe;
import com.sandesh.RecipeManagementSystem.repository.RecipeRepo;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RecipeService {
    @Autowired
    RecipeRepo recipeRepo;

    @Autowired
    CommentService commentService;

    public String addRecipe(Recipe newRecipe) {
        newRecipe.setRecipePostTimeStamp(LocalDateTime.now());
        recipeRepo.save(newRecipe);
        return null;
    }

    public Recipe getRecipeByName(String name) {
        return recipeRepo.findFirstByRecipeName(name);
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepo.findById(id).orElse(null);
    }

    public void updateRecipeById(Recipe recipe, Long id) {
        Recipe existingRecipe = recipeRepo.findById(id).orElse(null);
        existingRecipe.setRecipeName(recipe.getRecipeName());
        existingRecipe.setInstructions(recipe.getInstructions());
        existingRecipe.setIngredients(recipe.getIngredients());
        recipeRepo.save(existingRecipe);
    }

    @Transactional
    public void removeById(Long recipeId) {
        Recipe myRecipe = recipeRepo.findById(recipeId).orElse(null);

        commentService.clearAllComments(myRecipe);

        recipeRepo.deleteById(recipeId);
    }
}
