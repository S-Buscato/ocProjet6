package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.dto.UserSubscribeOkDTO;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.dto.UsersSubscribeDTO;
import com.paymybuddy.paymybuddy.exception.ExistingEmailException;
import com.paymybuddy.paymybuddy.exception.UserAllReadyExistException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.models.Users;

import java.util.List;
import java.util.Optional;

public interface IUsersService {
    Iterable<UsersDTO> findall();
    UsersMinimalsInfoDTO findUsersFriends(Long id) throws UsersNotFoundException;

    UsersDTO findUserInfo(Long id) throws UsersNotFoundException;

    Optional<Users> findByEmail(String email);

    Users findById(Long id);
    Long deleteById(Long id);
    Users update(Users users, Long id);
    Users findByfirstNameAndLastName(String firstName, String lastName);
    Iterable<Users> saveAll(List<Users> lstPerson);

    Users save(Users users);

    UserSubscribeOkDTO subscribe(UsersSubscribeDTO usersSubscribeDTO) throws ExistingEmailException;

    UsersMinimalsInfoDTO addFriends(Long userId, UsersMinimalsInfoDTO usersMinimalsInfoDTO) throws UsersNotFoundException, UserAllReadyExistException;

    Object removeFriends(Long userId, UsersMinimalsInfoDTO usersMinimalsInfoDTO);
}
