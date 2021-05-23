package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersFriendsDTO;
import com.paymybuddy.paymybuddy.models.Users;

import java.util.List;

public interface IUsersService {
    Iterable<Users> findall();
    UsersFriendsDTO findUsersFriends(Long id);
    Users findById(Long id);
    Long deleteById(Long id);
    Users update(Users users, Long id);
    Users findByfirstNameAndLastName(String firstName, String lastName);
    Iterable<Users> saveAll(List<Users> lstPerson);

    Users save(Users users);

    Users addFriends(Long userId, Long usersFriendId);
    UsersDTO removeFriends(Long userId, Long usersFriendId);
}
