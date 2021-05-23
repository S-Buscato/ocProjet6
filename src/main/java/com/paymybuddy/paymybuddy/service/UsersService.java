package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.controller.UsersController;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersFriendsDTO;
import com.paymybuddy.paymybuddy.dto.mapper.UsersMapper;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.iservice.IUsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsersService implements IUsersService {

    static Logger logger = Logger.getLogger(UsersController.class);

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TransactionRepository transactionRepository;

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
            usersFriendsDTO = UsersMapper.INSTANCE.convertUsersToUsersFriendsDTO(usersRepository.findById(id).get());
           // usersFriendsDTO.setFriendsList(UsersMapper.INSTANCE.convertUsersToUsersDTOList(usersRepository.findById(id).get().getFriends()));
            logger.info("users findAllFriends");
        }
        return usersFriendsDTO;
    }


    @Override
    public Users findById(Long id) {
        logger.info("find by id " + id);
        return usersRepository.findById(id).get();
    }

    @Override
    public Long deleteById(Long id) {
        return null;
    }

    @Override
    public Users save(Users users) {
        return usersRepository.save(users);
    }

    @Override
    public Users addFriends(Long userId, Long usersFriendId){
        Users users = findById(userId);
        Users userFriends = findById(usersFriendId);
        List<Users> usersList = new ArrayList<>();
        if(users.getFriends().size() != 0){
            usersList = users.getFriends();
        }
        usersList.add(userFriends);
        users.setFriends(usersList);
        return usersRepository.save(users);
    }

    @Override
    public UsersDTO removeFriends(Long userId, Long usersFriendId) {
        Users users = findById(userId);
        Users userFriends = findById(usersFriendId);
        List<Users> usersList = new ArrayList<>();
        if(users.getFriends().size() != 0){
            usersList=users.getFriends();
        }
        usersList.remove(userFriends);
        users.setFriends(usersList);
        UsersDTO usersDTO = UsersMapper.INSTANCE.convertUsersToUsersDTO(usersRepository.save(users));
        return usersDTO;
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
