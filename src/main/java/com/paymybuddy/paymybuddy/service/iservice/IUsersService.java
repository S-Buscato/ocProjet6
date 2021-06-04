package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersFriendsDTO;
import com.paymybuddy.paymybuddy.models.Users;

import java.util.List;
import java.util.Optional;

public interface IUsersService {
<<<<<<< Updated upstream
    Iterable<Users> findall();
    UsersFriendsDTO findUsersFriends(Long id);
    Optional<Users> findById(Long id);
    Long deleteById(Long id);
    Users save(UsersDTO usersDTO);
    Users update(Users users, Long id);
    Users findByfirstNameAndLastName(String firstName, String lastName);
    Iterable<Users> saveAll(List<Users> lstPerson);
    Users addFriends(Long userId, Long usersFriendId);
    Users removeFriends(Long userId, Long usersFriendId);
    Users sendMoneyToFriends(Long userId, Long usersFriendId, double amount);
=======

    Iterable<UsersDTO> findall();

    UsersMinimalsInfoDTO findUsersFriends(Long id) throws UsersNotFoundException;

    UsersDTO findUserInfo(Long id) throws UsersNotFoundException;

    Optional<Users> findById(Long id);

    Long deleteById(Long id) throws UsersNotFoundException;

    Users save(Users users);

    UsersDTO update(UsersDTO usersDTO, Long id) throws UsersNotFoundException;

    UserSubscribeOkDTO subscribe(UsersSubscribeDTO usersSubscribeDTO) throws ExistingEmailException;

    UsersMinimalsInfoDTO addFriends(Long userId, UsersMinimalsInfoDTO usersMinimalsInfoDTO) throws UsersNotFoundException, UserAllReadyExistException;

    UsersMinimalsInfoDTO removeFriends(Long userId, UsersMinimalsInfoDTO usersMinimalsInfoDTO) throws UsersNotFoundException;

    Optional<Users> findByEmail(String s);
>>>>>>> Stashed changes
}
