package com.sandesh.RecipeManagementSystem.service;

import com.sandesh.RecipeManagementSystem.model.AuthenticationToken;
import com.sandesh.RecipeManagementSystem.model.Comment;
import com.sandesh.RecipeManagementSystem.model.Dto.AuthenticationInputDto;
import com.sandesh.RecipeManagementSystem.model.Recipe;
import com.sandesh.RecipeManagementSystem.model.User;
import com.sandesh.RecipeManagementSystem.repository.UserRepo;
import com.sandesh.RecipeManagementSystem.service.HashingUtility.PasswordEncryptor;
import com.sandesh.RecipeManagementSystem.service.mailUtility.EmailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    CommentService commentService;

    public String userSignUp(User newUser) {
        String newEmail = newUser.getUserEmail();

        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if(existingUser != null){
            return "email already in use";
        }
        String signUpPassword = newUser.getUserPassword();

        try{
            String encryptedPassword =  PasswordEncryptor.encryptPassword(signUpPassword);

            newUser.setUserPassword(encryptedPassword);

            userRepo.save(newUser);
            return "user registered successfully!!!";
        }
        catch(NoSuchAlgorithmException e){
            return"Internal Server issues while saving password, try again later!!!";
        }
    }

    public String userSignIn(String email, String password) {
        User existingUser = userRepo.findFirstByUserEmail(email);

        if(existingUser == null)
        {
            return "Not a valid email, Please sign up first !!!";
        }



        try {
            String encryptedPassword = PasswordEncryptor.encryptPassword(password);

            if(existingUser.getUserPassword().equals(encryptedPassword))
            {

                AuthenticationToken token  = new AuthenticationToken(existingUser);

                if(EmailHandler.sendEmail(email,"otp after login", token.getTokenValue())) {
                    authenticationService.createToken(token);
                    return "check email for otp/token!!!";
                }
                else {
                    return "error while generating token!!!";
                }

            }
            else {

                return "Invalid Credentials!!!";
            }

        } catch (NoSuchAlgorithmException e) {

            return "Internal Server issues while saving password, try again later!!!";
        }
    }


    public String userSignOut(String email,String tokenValue) {
        if(authenticationService.authenticate(email,tokenValue)) {
            authenticationService.deleteToken(tokenValue);
            return "Sign Out successful!!";
        }
        else {
            return "Un Authenticated access!!!";
        }
    }

    public String addRecipe(String email, String tokenValue, Recipe newRecipe) {
        if(authenticationService.authenticate(email,tokenValue)){

            User existingUser = userRepo.findFirstByUserEmail(email);
            newRecipe.setRecipeOwner(existingUser);

            return recipeService.addRecipe(newRecipe);
        }
        else{
            return "Un Authenticated access!!!";
        }
    }

    public Recipe getRecipeByName(String email, String tokenValue, String name) {
        if(authenticationService.authenticate(email,tokenValue)){

            Recipe existingRecipe = recipeService.getRecipeByName(name);
            String recipeOwnerEmail = existingRecipe.getRecipeOwner().getUserEmail();
            if(email.equals(recipeOwnerEmail)){
                return recipeService.getRecipeByName(name);
            }
            else {
                return null;
            }
        }else{
            return null;
        }

    }

    public String updateByRecipeId(String email, String tokenValue, Recipe recipe,Long id) {
        if(authenticationService.authenticate(email,tokenValue)){

            Recipe existingRecipe = recipeService.getRecipeById(id);
            String recipeOwnerEmail = existingRecipe.getRecipeOwner().getUserEmail();

            if(email.equals(recipeOwnerEmail)){

                 recipeService.updateRecipeById(recipe,id);
                 return "recipe updated";
            }
            else {
                return "Un authorized access!!";
            }
        }
        else{
            return "Un Authenticated access!!!";
        }
    }

    public String removeRecipeById(String email, String tokenValue,Long recipeId) {
        if(authenticationService.authenticate(email,tokenValue)){

            Recipe existingRecipe = recipeService.getRecipeById(recipeId);
            String recipeOwnerEmail = existingRecipe.getRecipeOwner().getUserEmail();

            if(email.equals(recipeOwnerEmail)){

                recipeService.removeById(recipeId);
                return "recipe deleted";
            }
            else {
                return "Un authorized access!!";
            }
        }
        else{
            return "Un Authenticated access!!!";
        }
    }

    public String addComment(String email, String tokenValue, Long postId,String commentBody) {
        if(authenticationService.authenticate(email,tokenValue)){

            Recipe recipeToBeCommented = recipeService.getRecipeById(postId);

            User commenter = userRepo.findFirstByUserEmail(email);

            Comment newComment = new Comment(null,commentBody,LocalDateTime.now(),recipeToBeCommented,commenter);

            commentService.addComment(newComment);

            return "commented on " + postId;
        }
        else {
            return "Un Authenticated access";
        }
    }

    public String removeComment(String email, String tokenValue, Long commentId) {
        if(authenticationService.authenticate(email,tokenValue)) {
            Comment comment = commentService.findCommentById(commentId);

            Recipe recipePostOfComment = comment.getPostRecipe();


            if (authorizedCommentRemover(email, recipePostOfComment, comment)) {
                commentService.removeCommentById(commentId);
                return "comment deleted";
            }

        else {
                return "Un Authenticated access!!!";
            }
        }
        else{
            return "Not authorized!!";
        }
    }

    private boolean authorizedCommentRemover(String email, Recipe recipePostOfComment, Comment comment) {
        User potentialRemover = userRepo.findFirstByUserEmail(email);

        return potentialRemover.equals(recipePostOfComment.getRecipeOwner()) || potentialRemover.equals(comment.getCommenter());
    }
}
