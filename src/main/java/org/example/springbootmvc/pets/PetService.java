package org.example.springbootmvc.pets;

import org.example.springbootmvc.users.UserService;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PetService {

    private final AtomicLong petIdCounter;
    private final UserService userService;

    public PetService(UserService userService) {
        this.petIdCounter = new AtomicLong();
        this.userService = userService;
    }

    public Pet createPet(Pet petToCreate) {
        if(petToCreate.id() != null) {
            throw new IllegalArgumentException("Id for pet should be not be provided");
        }
        var petToSave = new Pet(petIdCounter.incrementAndGet() ,petToCreate.name(), petToCreate.userId());
        userService.getUserById(petToCreate.userId())
                .pets()
                .add(petToSave);
        return petToSave;
    }


    public Pet getPetById(Long id) {
        return findPetById(id)
                .orElseThrow(() -> new NoSuchElementException("No such pet with id=%s".formatted(id)));
    }

    public Pet updatePet(Pet petToUpdate) {
        if(petToUpdate.id() == null) {
            throw new IllegalArgumentException("No user id passed");
        }

        var foundPet = findPetById(petToUpdate.id())
                .orElseThrow(() -> new NoSuchElementException("No such pet with id=%s".formatted(petToUpdate.id())));
        var updatedPet = new Pet(foundPet.id(), petToUpdate.name(), petToUpdate.userId());

        var user = userService.getUserById(petToUpdate.userId());
        user.pets().remove(foundPet);
        user.pets().add(updatedPet);
        return updatedPet;
    }

    public void deletePet(Long id) {
        var pet = findPetById(id)
                .orElseThrow(() -> new NoSuchElementException("No such pet with id=%s".formatted(id)));
        var user = userService.getUserById(pet.userId());
        user.pets().remove(pet);
        }

    private Optional<Pet> findPetById(Long id) {
        return userService.getAllUsers().stream()
                .flatMap(user -> user.pets().stream())
                .filter(pet -> pet.id().equals(id))
                .findAny();
    }
}
