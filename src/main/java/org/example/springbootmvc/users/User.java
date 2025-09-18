package org.example.springbootmvc.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.example.springbootmvc.pets.Pet;

import java.util.List;

public record User (
    @Null
    Long id,

    @NotNull
    @NotBlank
    String name,

    @NotNull
    @Email
    String email,

    Integer age,


    List<Pet> pets
) {
}
