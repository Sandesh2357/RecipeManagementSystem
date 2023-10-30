package com.sandesh.RecipeManagementSystem.model.Dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationInputDto {

    @Email
    private String email;

    private String tokenValue;
}
