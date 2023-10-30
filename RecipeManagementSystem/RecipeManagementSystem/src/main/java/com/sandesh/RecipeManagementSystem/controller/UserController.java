package com.sandesh.RecipeManagementSystem.controller;


import com.sandesh.RecipeManagementSystem.model.Dto.AuthenticationInputDto;
import com.sandesh.RecipeManagementSystem.model.Recipe;
import com.sandesh.RecipeManagementSystem.model.User;
import com.sandesh.RecipeManagementSystem.service.AuthenticationService;
import com.sandesh.RecipeManagementSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("user/signUp")
    public String userSignUp(@Valid @RequestBody User newUser){
        return userService.userSignUp(newUser);
    }

    @PostMapping("user/signIn/{email}/{password}")
    public String userSignIn(@PathVariable String email, @PathVariable String password){
        return userService.userSignIn(email,password);
    }

    @DeleteMapping("user/signOut")
    public String userSignOut(@RequestParam String email, @RequestParam String token){
        return userService.userSignOut(email,token);
    }
     @PostMapping("recipe")
    public String addRecipe(@RequestParam String email, @RequestParam String tokenValue, @RequestBody Recipe newRecipe){
        return userService.addRecipe(email,tokenValue,newRecipe);
     }

     @GetMapping("recipe/name/{name}")
    public Recipe getRecipeByName(@RequestParam String email, @RequestParam String tokenValue,@PathVariable String name){
        return userService.getRecipeByName(email,tokenValue,name);
     }
     @PutMapping("recipe/id/{id}")
    public String updateRecipeById(@RequestParam String email, @RequestParam String tokenValue,@RequestBody Recipe recipe,@PathVariable Long id){
        return userService.updateByRecipeId(email,tokenValue,recipe,id);
     }
     @DeleteMapping("recipe/id/{recipeId}")
     public String removeRecipeById(@RequestParam String email, @RequestParam String tokenValue,@PathVariable Long recipeId){
        return userService.removeRecipeById(email,tokenValue,recipeId);
     }

    @PostMapping("Comment/id/{postId}")
    public String addComment(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Long postId,@RequestBody String commentBody){
        return userService.addComment(email,tokenValue,postId,commentBody);
    }

    @DeleteMapping("comment/commentId/{commentId}")
    public String removeComment(@RequestParam String email, @RequestParam String tokenValue,@PathVariable Long commentId){
        return userService.removeComment(email,tokenValue,commentId);
    }


}
