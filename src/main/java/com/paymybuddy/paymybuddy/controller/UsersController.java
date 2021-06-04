package com.paymybuddy.paymybuddy.controller;


import com.paymybuddy.paymybuddy.dto.UserSubscribeOkDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.dto.mapper.UsersMapper;
import com.paymybuddy.paymybuddy.dto.UsersSubscribeDTO;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.dto.UserSubscribeOkDTO;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.dto.UsersSubscribeDTO;
import com.paymybuddy.paymybuddy.exception.ExistingEmailException;
import com.paymybuddy.paymybuddy.exception.UserAllReadyExistException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;

import com.paymybuddy.paymybuddy.exception.ExistingEmailException;
import com.paymybuddy.paymybuddy.exception.UserAllReadyExistException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.service.UsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    static Logger logger = Logger.getLogger(UsersController.class);

    @GetMapping("/paymybody/users")
    public ResponseEntity<List<UsersDTO>> findAllUsers() {
        try {
            ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(usersService.findall());
            logger.info("/users findAllUsers");
            return response;
        }
        catch (Exception e) {
            logger.error("/users findAllUsers => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paymybody/findUsersFriends/{id}")
    public ResponseEntity<UsersMinimalsInfoDTO> findUsersFriends(@PathVariable Long id) {
        try {
            logger.info("/findUsersFriends/{id}");
            ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(usersService.findUsersFriends(id));
            return response;
        }
        catch (UsersNotFoundException e) {
            logger.error("/findUsersFriends/{id} => users not found : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            logger.error("/findUsersFriends/{id} => error : " + e);
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paymybody/users/{id}")
    public ResponseEntity<UsersDTO> getUsersInfo(@PathVariable Long id) {
        try {
            logger.info("/users/{id} getUsersInfo");
            ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(usersService.findUserInfo(id));
            return response;
        }
        catch (UsersNotFoundException e) {
            logger.error("/users/{id} getUsersInfo => users not found : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            logger.error("/users/{id} getUsersInfo => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("subscribe")
    public ResponseEntity<UserSubscribeOkDTO> subscribe(@RequestBody UsersSubscribeDTO usersSubscribeDTO) {
        try {
            logger.info("/subscribe");
            ResponseEntity response = ResponseEntity.status(HttpStatus.CREATED).body(usersService.subscribe(usersSubscribeDTO));
            return response;
        }
        catch (ExistingEmailException e) {
            logger.error("/subscribe => inscription impossible : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            logger.error("/subscribe => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/paymybody/users/addfriends/{userId}")
    public ResponseEntity<UsersMinimalsInfoDTO> addFriends(@PathVariable("userId") Long userId,
                                                           @RequestBody UsersMinimalsInfoDTO usersMinimalsInfoDTO) {
        try {
            ResponseEntity response = ResponseEntity.status(HttpStatus.ACCEPTED).body(usersService.addFriends(userId, usersMinimalsInfoDTO));
            logger.info("/users/addfriends/{userId}");
            return response;

        }
        catch (UsersNotFoundException e) {
            logger.error("/users/addfriends/{userId} => ajout impossible : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (UserAllReadyExistException e) {
            logger.error("/users/addfriends/{userId} => ajout impossible : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            logger.error("/users/addfriends/{userId} => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/paymybody/users/removefriends/{userId}")
    public ResponseEntity<UsersMinimalsInfoDTO> removeFriends(@PathVariable("userId") Long userId,
                                                              @RequestBody UsersMinimalsInfoDTO usersMinimalsInfoDTO) {
        try {
            ResponseEntity response = ResponseEntity.status(HttpStatus.CREATED).body(usersService.removeFriends(userId, usersMinimalsInfoDTO));
            logger.info("/users/removefriends");
            return response;
        }
        catch (Exception e) {
            logger.error("/users/removefriends => error : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
