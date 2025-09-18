package org.example.springbootmvc.pets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springbootmvc.users.User;
import org.example.springbootmvc.users.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {


    private static final Logger log = LoggerFactory.getLogger(PetControllerTest.class);

    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PetService petService;

    @Test
    void shouldSuccessCreatePet() throws Exception {
        var user = userService.createUser(new User(
                null,
                "Vadim",
                "mail@mail.com",
                25,
                List.of()
        ));

        var petDto = new PetDto(
                null,
                "some-name",
                user.id()
        );

        String petJson = objectMapper.writeValueAsString(petDto);

        String createPetJson = mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
                )
                .andExpect(status().is(201))
                .andReturn().
                getResponse().
                getContentAsString();

        var petDtoResponse = objectMapper.readValue(createPetJson, PetDto.class);

        Assertions.assertEquals(petDto.name(), petDtoResponse.name());
        Assertions.assertEquals(petDto.userId(), petDtoResponse.userId());
        Assertions.assertNotNull(petDtoResponse.id());

        Assertions.assertDoesNotThrow(() -> petService.getPetById(petDtoResponse.id()));

        var userWithPet = userService.getUserById(user.id());
        Assertions.assertEquals(1, userWithPet.pets().size());
        Assertions.assertEquals(petDtoResponse.id(), userWithPet.pets().get(0).id());
    }

    @Test
    void shouldDeletePet() throws Exception {
        var user = userService.createUser(new User(
                null,
                "Vadim",
                "mail@mail.com",
                25,
                List.of()
        ));

        var pet = petService.createPet(new Pet(
                null,
                "some-name",
                user.id()
        ));

        mockMvc.perform(delete("/pets/{id}", pet.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> petService.getPetById(pet.id())
        );

        var userAfterDelete = userService.getUserById(user.id());
        Assertions.assertTrue(userAfterDelete.pets().isEmpty());

        log.info("Pet with id={} has gone to a better place 🐾🌈", pet.id());
    }

}