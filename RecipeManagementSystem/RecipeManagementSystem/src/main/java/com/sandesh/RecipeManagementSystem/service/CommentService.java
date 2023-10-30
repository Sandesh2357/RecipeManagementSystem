package com.sandesh.RecipeManagementSystem.service;

import com.sandesh.RecipeManagementSystem.model.Comment;
import com.sandesh.RecipeManagementSystem.model.Recipe;
import com.sandesh.RecipeManagementSystem.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepo commentRepo;


    public void addComment(Comment newComment) {
        commentRepo.save(newComment);
    }

    public Comment findCommentById(Long commentId) {
        return commentRepo.findById(commentId).orElse(null);
    }

    public void removeCommentById(Long commentId) {

        commentRepo.deleteById(commentId);
    }

    public void clearAllComments(Recipe myRecipe) {
        List<Comment> commentsOfRecipe = commentRepo.findByPostRecipe(myRecipe);
        commentRepo.deleteAll(commentsOfRecipe);
    }
}
