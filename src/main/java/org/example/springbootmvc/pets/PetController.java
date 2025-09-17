//package org.example.springbootmvc.pets;
//
//import jakarta.validation.Valid;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//public class PetController {
//
//    private static final Logger log = LoggerFactory.getLogger(PetController.class);
//    private final PetService petService;
//
//    public PetController(PetService petService) {
//        this.petService = petService;
//    }
//
//    @PostMapping("/pets")
//    public ResponseEntity<PetDto> createPet(
//            @RequestBody @Valid PetDto petToCreate
//    ) {
//        log.info("Get request for create user:{}", petToCreate);
//        var createdPet = petService.createPet(petToCreate, );
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(createdUser);
//    }
//
//    @PutMapping("/pets/{id}")
//    public UserDto updateUser(
//            @PathVariable("id") Long id,
//            @RequestBody @Valid UserDto userToUpdate
//    ) {
//        log.info("Get request for update user: id={}, userToUpdate={}", id, userToUpdate);
//        return petService.updateUser(id, userToUpdate);
//    }
//
//    @DeleteMapping("/pets/{id}")
//    public  ResponseEntity<UserDto> deleteUserById(
//            @PathVariable ("id") Long id,
//            @RequestBody @Valid UserDto userToDelete
//    ) {
//        petService.deleteUser(id);
//
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//
//    @GetMapping("/pets/{id}")
//    public UserDto getUserById(
//            @PathVariable ("id") Long id
//    ) {
//        log.info("Get request for get user by id:{}", id);
//        return petService.getUserById(id);
//    }
//}
