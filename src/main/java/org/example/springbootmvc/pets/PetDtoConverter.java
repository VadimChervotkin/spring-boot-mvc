package org.example.springbootmvc.pets;

import org.springframework.stereotype.Component;

@Component
public class PetDtoConverter {

    public PetDto toDto(Pet pet) {
        return new PetDto(
                pet.id(),
                pet.name(),
                pet.userId()
        );
    }

    public Pet toPet(PetDto petDto) {
        return new Pet(
                petDto.id(),
                petDto.name(),
                petDto.userId()
        );
    }
}
