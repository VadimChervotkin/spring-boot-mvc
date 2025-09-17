package org.example.springbootmvc.pets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record PetDto (

    @Null
    Long id,

    @NotNull
    @NotBlank
    String name,

    @NotBlank
    @NotNull
    Long userId
) {

}

