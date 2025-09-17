package org.example.springbootmvc.users;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final AtomicLong idCounter;
    private final Map<Long, User> usersMap;

    public UserService() {
        this.idCounter = new AtomicLong();
        this.usersMap = new ConcurrentHashMap<>();
    }

    public User createUser(User userToCreate) {
        if (userToCreate.id() != null) {
            throw new IllegalArgumentException("Id for user should not be provided");
        }
        if (userToCreate.pets() != null && !userToCreate.pets().isEmpty()) {
            throw new IllegalArgumentException("User pets must be empty");
        }

        var newId = idCounter.incrementAndGet();
        var newUser = new User(
                newId,
                userToCreate.name(),
                userToCreate.email(),
                userToCreate.age(),
                new ArrayList<>()
        );
        usersMap.put(newId, newUser);
        return newUser;
    }

    public User updateUser(User userToUpdate) {
        if (userToUpdate.id() == null) {
            throw new IllegalArgumentException("No user id passed");
        }
        if (!usersMap.containsKey(userToUpdate.id())) {
            throw new NoSuchElementException("No such user with id =%s".formatted(userToUpdate.id()));
        }

        usersMap.put(userToUpdate.id(), userToUpdate);

        return userToUpdate;
    }

    public void deleteUser(Long id) {
        var result = usersMap.remove(id);
        if (result == null) {
            throw new NoSuchElementException("No found user by id=%s".formatted(id));
        }
    }

    public User getUserById(Long id) {
        if (!usersMap.containsKey(id)) {
            throw new NoSuchElementException("No such user with id =%s".formatted(id));
        }
        return usersMap.get(id);
    }
}
