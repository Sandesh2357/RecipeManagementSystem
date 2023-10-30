package com.sandesh.RecipeManagementSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @NotBlank
    private String recipeName;

    @Lob
    private String ingredients;

    @Lob
    private String instructions;

    private LocalDateTime recipePostTimeStamp;


    @ManyToOne
    @JoinColumn(name = "fk_owner_user_id")
    private User recipeOwner;
}
