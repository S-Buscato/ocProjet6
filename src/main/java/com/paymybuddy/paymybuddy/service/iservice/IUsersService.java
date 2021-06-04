package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.dto.UserSubscribeOkDTO;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.dto.UsersSubscribeDTO;
import com.paymybuddy.paymybuddy.exception.ExistingEmailException;
import com.paymybuddy.paymybuddy.exception.UserAllReadyExistException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.models.Users;

import java.util.Optional;

public interface IUsersService {

    Users findById(Long id);
    Optional<Users> findByEmail(String email);
    Iterable<UsersDTO> findall() throws UsersNotFoundException;
    Long deleteById(Long id) throws UsersNotFoundException;
    UsersDTO findUserInfo(Long id) throws UsersNotFoundException;
    UsersMinimalsInfoDTO findUsersFriends(Long id) throws UsersNotFoundException;
    Users save(Users users);
    UsersDTO update(UsersDTO usersDTO, Long id) throws UsersNotFoundException;
    UserSubscribeOkDTO subscribe(UsersSubscribeDTO usersSubscribeDTO) throws ExistingEmailException;
    UsersMinimalsInfoDTO addFriends(Long userId, UsersMinimalsInfoDTO usersMinimalsInfoDTO) throws UsersNotFoundException, UserAllReadyExistException;
    UsersMinimalsInfoDTO removeFriends(Long userId, UsersMinimalsInfoDTO usersMinimalsInfoDTO) throws UsersNotFoundException;

}
