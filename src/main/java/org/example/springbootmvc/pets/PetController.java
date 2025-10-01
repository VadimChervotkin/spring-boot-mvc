package org.example.springbootmvc.pets;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class PetController {

    private static final Logger log = LoggerFactory.getLogger(PetController.class);
    private final PetService petService;
    private final PetDtoConverter petDtoConverter;

    public PetController(PetService petService, PetDtoConverter petDtoConverter) {
        this.petService = petService;
        this.petDtoConverter = petDtoConverter;
    }

    @PostMapping("/pets")
    public ResponseEntity<PetDto> createPet(
            @RequestBody @Validated PetDto petToCreate
    ) {
        log.info("Get request for create pet:{}", petToCreate);
        var createdPet = petService.createPet(petDtoConverter.toPet(petToCreate));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(petDtoConverter.toDto(createdPet));
    }

    @PutMapping("/pets/{id}")
    public ResponseEntity<PetDto> updatePet(
            @PathVariable ("id") Long id,
            @RequestBody @Validated PetDto pet
    ) {
        log.info("Get request for update pet: id={}, pet={}", id, pet);
        var petToUpdate = new Pet(
                id,
                pet.name(),
                pet.userId()
        );
        var updatedPet = petService.updatePet(petToUpdate);
        return ResponseEntity.ok(petDtoConverter.toDto(updatedPet));
    }

    @DeleteMapping("/pets/{id}")
    public  ResponseEntity<Void> deletePetById(
            @PathVariable ("id") Long id
    ) {
        log.info("DELETE request for delete pet id={}", id);
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<PetDto> getPetById(
            @PathVariable ("id") Long id
    ) {
        log.info("Get request for get pet by id:{}", id);
        var pet = petService.getPetById(id);
        return ResponseEntity.ok(petDtoConverter.toDto(pet));
    }
}
