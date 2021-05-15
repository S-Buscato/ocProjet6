package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.dto.UsersFriendsDTO;
import com.paymybuddy.paymybuddy.models.Users;

import java.util.List;
import java.util.Optional;

public interface IUsersService {
    Iterable<Users> findall();
    UsersFriendsDTO findUsersFriends(Long id);
    Optional<Users> findById(Long id);
    Long deleteById(Long id);
    Users save(Users users);
    Users update(Users users, Long id);
    Users findByfirstNameAndLastName(String firstName, String lastName);
    Iterable<Users> saveAll(List<Users> lstPerson);
    Users addFriends(Long userId, Long usersFriendId);
    Users removeFriends(Long userId, Long usersFriendId);
}
