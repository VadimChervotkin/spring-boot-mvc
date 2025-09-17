//package org.example.springbootmvc.pets;
//
//import org.example.springbootmvc.users.UserService;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//@Service
//public class PetService {
//
//    private  long idCounter;
//    private Map<Long, PetDto> petsMap;
//    private UserDto userDto;
//    private UserService userService;
//
//
//    public PetDto createPet(PetDto peToCreate, String userName) {
//        var newId = ++idCounter;
//        var newPet = new PetDto(
//                newId,
//                peToCreate.getName(),
//                userDto.getId()
//        );
//        petsMap.put(newId, newPet);
//        for (UserDto user : userService.getUsersMap().values()) {
//            if (user.getName().equals(userName)) {
//                user.getPets().add(newPet);
//            }
//        }
//
//        return newPet;
//    }
//
//    public PetDto updatePet(Long id, PetDto petToUpdate) {
//        if (petsMap.get(id) == null) {
//            throw new NoSuchElementException("No found user by id=%s".formatted(id));
//        }
//        var updatedPet = new PetDto(
//                id,
//                petToUpdate.getName(),
//                petToUpdate.getUserId()
//        );
//        petsMap.put(id, updatedPet);
//        return updatedPet;
//    }
//
//    public PetDto getPetrById(Long id) {
//        return Optional.ofNullable(petsMap.get(id))
//                .orElseThrow(()-> new NoSuchElementException("No found user by id=%s".formatted(id)
//                ));
//    }
//
//
//    public void deletePet(Long id, String userName) {
//        var result = petsMap.remove(id);
//        if (result == null) {
//            throw new NoSuchElementException("No found user by id=%s".formatted(id));
//        }
//        for (UserDto user : userService.getUsersMap().values()) {
//            if (user.getName().equals(userName)) {
//                user.getPets().remove(result);
//            }
//        }
//    }
//}
