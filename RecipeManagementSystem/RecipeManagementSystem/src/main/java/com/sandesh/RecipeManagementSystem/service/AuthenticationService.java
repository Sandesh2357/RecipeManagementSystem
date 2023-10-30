package com.sandesh.RecipeManagementSystem.service;


import com.sandesh.RecipeManagementSystem.model.AuthenticationToken;
import com.sandesh.RecipeManagementSystem.repository.AuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationRepo authenticationRepo;



    public void createToken(AuthenticationToken token) {
        authenticationRepo.save(token);
    }

    public  boolean authenticate(String email, String tokenValue) {
        AuthenticationToken token =  authenticationRepo.findFirstByTokenValue(tokenValue);
        if(token!=null)
        {
            return token.getUser().getUserEmail().equals(email);
        }
        else
        {
            return false;
        }
    }

    public void deleteToken(String tokenValue) {
        AuthenticationToken token =  authenticationRepo.findFirstByTokenValue(tokenValue);
        authenticationRepo.delete(token);
    }
}
