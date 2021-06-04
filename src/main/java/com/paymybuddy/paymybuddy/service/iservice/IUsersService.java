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
<<<<<<< HEAD
<<<<<<< Updated upstream
    Iterable<Users> findall();
    UsersFriendsDTO findUsersFriends(Long id);
    Optional<Users> findById(Long id);
=======
    Iterable<UsersDTO> findall();
    UsersMinimalsInfoDTO findUsersFriends(Long id) throws UsersNotFoundException;

    UsersDTO findUserInfo(Long id) throws UsersNotFoundException;

    Optional<Users> findByEmail(String email);

    Users findById(Long id);
>>>>>>> cdf3b60668139198638b728d256ba8860567095d
    Long deleteById(Long id);
    Users update(Users users, Long id);
    Users findByfirstNameAndLastName(String firstName, String lastName);
    Iterable<Users> saveAll(List<Users> lstPerson);
<<<<<<< HEAD
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

=======

    Users save(Users users);

>>>>>>> cdf3b60668139198638b728d256ba8860567095d
    UserSubscribeOkDTO subscribe(UsersSubscribeDTO usersSubscribeDTO) throws ExistingEmailException;

    UsersMinimalsInfoDTO addFriends(Long userId, UsersMinimalsInfoDTO usersMinimalsInfoDTO) throws UsersNotFoundException, UserAllReadyExistException;

<<<<<<< HEAD
    UsersMinimalsInfoDTO removeFriends(Long userId, UsersMinimalsInfoDTO usersMinimalsInfoDTO) throws UsersNotFoundException;

    Optional<Users> findByEmail(String s);
>>>>>>> Stashed changes
=======
    Object removeFriends(Long userId, UsersMinimalsInfoDTO usersMinimalsInfoDTO);
>>>>>>> cdf3b60668139198638b728d256ba8860567095d
}
