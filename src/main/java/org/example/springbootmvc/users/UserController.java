package org.example.springbootmvc.users;

import org.example.springbootmvc.pets.PetDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserDtoConverter userDtoConverter;
    private final PetDtoConverter petDtoConverter;

    public UserController(UserService userService, UserDtoConverter userDtoConverter, PetDtoConverter petDtoConverter) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.petDtoConverter = petDtoConverter;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(
            @RequestBody @Validated UserDto userToCreate
    ) {
        log.info("Get request for create user:{}", userToCreate);
        var createdUser = userService.createUser(userDtoConverter.toUser(userToCreate));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDtoConverter.toDto(createdUser));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable ("id") Long id,
            @RequestBody @Validated UserDto userToUpdate
    ) {
        log.info("Get request for update user: id={}, userToUpdate={}", id, userToUpdate);
        var user = new User(
                id,
                userToUpdate.name(),
                userToUpdate.email(),
                userToUpdate.age(),
                userToUpdate.pets().stream().map(petDtoConverter::toPet).toList()
        );
        var updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(userDtoConverter.toDto(updatedUser));
    }

    @DeleteMapping("/users/{id}")
    public  ResponseEntity<Void> deleteUserById(
            @PathVariable ("id") Long id
    ) {
        log.info("DELETE request for delete user id={}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable ("id") Long id
    ) {
        log.info("Get request for get user by id:{}", id);
        var user = userService.getUserById(id);
        return ResponseEntity.ok(userDtoConverter.toDto(user));
    }
}
