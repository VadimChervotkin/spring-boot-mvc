package org.example.springbootmvc.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.example.springbootmvc.pets.PetDto;


import java.util.List;

public record UserDto(

        @Null
        Long id,

        @NotNull
        @NotBlank
        String name,

        @NotNull
        @Email
        String email,

        @NotNull
        Integer age,


        List<PetDto> pets
) {
}


