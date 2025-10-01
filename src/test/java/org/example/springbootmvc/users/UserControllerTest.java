package org.example.springbootmvc.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSuccessCreateUser() throws Exception {

        var userDto = new UserDto(
                null,
                "some-user",
                "email@email.com",
                20,
                List.of()
        );

        String userJson = objectMapper.writeValueAsString(userDto);

        String createUserJson = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().is(201))
                .andReturn().
                getResponse().
                getContentAsString();

        var userDtoResponse = objectMapper.readValue(createUserJson, UserDto.class);

        Assertions.assertEquals(userDto.name(), userDtoResponse.name());
        Assertions.assertEquals(userDto.age(), userDtoResponse.age());
        Assertions.assertEquals(userDto.email(), userDtoResponse.email());
        Assertions.assertEquals(userDto.pets(), userDtoResponse.pets());

        Assertions.assertDoesNotThrow(()->userService.getUserById(userDtoResponse.id()));
    }

    @Test
    void shouldGetUserById() throws Exception {
        var createdUser = userService.createUser(new User(
                null,
                "user",
                "mail@mail.com",
                23,
                List.of()
        ));

        String getUserJson = mockMvc.perform(get("/users/{id}", createdUser.id())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var userDtoResponse = objectMapper.readValue(getUserJson, UserDto.class);

        Assertions.assertEquals(createdUser.id(), userDtoResponse.id());
        Assertions.assertEquals(createdUser.name(), userDtoResponse.name());
        Assertions.assertEquals(createdUser.age(), userDtoResponse.age());
        Assertions.assertEquals(createdUser.email(), userDtoResponse.email());
        Assertions.assertEquals(createdUser.pets(), userDtoResponse.pets());


    }

}