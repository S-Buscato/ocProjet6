package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.controller.UsersController;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersFriendsDTO;
import com.paymybuddy.paymybuddy.dto.mapper.UsersMapper;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.iservice.IUsersService;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UsersService implements IUsersService {

    static Logger logger = Logger.getLogger(UsersController.class);


    final UsersRepository usersRepository;

    @Override
    public List<Users> findall() {
        List<Users> ret = StreamSupport.stream(usersRepository.findAll().spliterator(),
                false).collect(Collectors.toList());
        logger.info("users findAll");
        return ret;
    }

    @Override
    public UsersFriendsDTO findUsersFriends(Long id) {
        UsersFriendsDTO usersFriendsDTO = new UsersFriendsDTO();
        if(usersRepository.findById(id).isPresent()){
            System.out.println(usersRepository.findById(id));
            usersFriendsDTO = UsersMapper.INSTANCE.convertUsersToUsersFriendsDTO(usersRepository.findById(id).get());
           // usersFriendsDTO.setFriendsList(UsersMapper.INSTANCE.convertUsersToUsersDTOList(usersRepository.findById(id).get().getFriends()));
            logger.info("users findAllFriends");
        }
        return usersFriendsDTO;
    }


    @Override
    public Optional<Users> findById(Long id) {
        logger.info("find by id " + id);
        return usersRepository.findById(id);
    }

    @Override
    public Long deleteById(Long id) {
        return null;
    }

    @Override
    public Users save(UsersDTO usersDTO) {
        Users users = UsersMapper.INSTANCE.convertUsersDTOToUsers(usersDTO);
        return usersRepository.save(users);
    }

    public UsersDTO addFriends(Long userId, Long usersFriendId){
        Users users = findById(userId).get();
        Users userFriends = findById(usersFriendId).get();
        List<Users> usersList = users.getFriends();
        usersList.add(userFriends);
        users.setFriends(usersList);

        return UsersMapper.INSTANCE.convertUsersToUsersDTO(usersRepository.save(users));
    }

    @Override
    public Users update(Users users, Long id) {
        return null;
    }

    @Override
    public Users findByfirstNameAndLastName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Iterable<Users> saveAll(List<Users> lstPerson) {
        return null;
    }
}
