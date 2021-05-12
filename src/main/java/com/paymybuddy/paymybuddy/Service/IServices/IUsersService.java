package com.paymybuddy.paymybuddy.Service.IServices;

import com.paymybuddy.paymybuddy.DTO.UsersDTO;
import com.paymybuddy.paymybuddy.DTO.UsersFriendsDTO;
import com.paymybuddy.paymybuddy.Models.Users;

import java.util.List;
import java.util.Optional;

public interface IUsersService {
    Iterable<Users> findall();
    UsersFriendsDTO findUsersFriends(Long id);
    Optional<Users> findById(Long id);
    Long deleteById(Long id);
    Users save(UsersDTO usersDTO);
    Users update(Users users, Long id);
    Users findByfirstNameAndLastName(String firstName, String lastName);
    Iterable<Users> saveAll(List<Users> lstPerson);
}
