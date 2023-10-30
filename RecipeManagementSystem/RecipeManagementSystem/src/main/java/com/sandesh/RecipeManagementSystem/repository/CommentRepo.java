package com.sandesh.RecipeManagementSystem.repository;

import com.sandesh.RecipeManagementSystem.model.Comment;
import com.sandesh.RecipeManagementSystem.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {

    List<Comment> findByPostRecipe(Recipe myRecipe);
}
