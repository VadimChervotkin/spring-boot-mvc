package org.example.springbootmvc.pets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Pet (
        Long id,

        @NotBlank
        String name,

        @NotNull
        Long userId
)
 {
}
