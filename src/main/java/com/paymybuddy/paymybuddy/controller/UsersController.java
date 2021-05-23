package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.mapper.UsersMapper;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.models.Users;
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

    /*@RequestMapping("/users/list")
    public String home(Model model)
    {
        model.addAttribute("users", usersService.findall());
        return "/users/list";
    }*/

    @GetMapping("/users/list")
    public ResponseEntity<List<UsersDTO>> findAllUsers() {
        try {
            ResponseEntity resp = ResponseEntity.ok(UsersMapper.INSTANCE.convertUsersToUsersDTOList(usersService.findall()));
            logger.info("/users (getAll) => ok");
            return resp;
        } catch (Exception e) {
            logger.error("/users (getAll) => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UsersDTO> findUsersFriends(@PathVariable Long id) {
        try {
            logger.info("/users (getOne) => ok");
            ResponseEntity resp = ResponseEntity.ok(usersService.findUsersFriends(id));
            return resp;
        } catch (Exception e) {
            logger.error("/users (getOne) => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/info/{id}")
    public ResponseEntity<UsersDTO> getUsersInfo(@PathVariable Long id) {
        try {
            logger.info("/users (getInfo) => ok");
            ResponseEntity resp = ResponseEntity.ok(UsersMapper.INSTANCE.convertUsersToUsersDTO(usersService.findById(id)));
            return resp;
        } catch (Exception e) {
            logger.error("/users (getInfo) => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/add")
    public ResponseEntity<Users> addPerson(@RequestBody Users users) {
        try {
            ResponseEntity resp = ResponseEntity.status(HttpStatus.CREATED).body(usersService.save(users));
            logger.info("/users/add => ok");
            return resp;

        } catch (Exception e) {
            logger.error("/users/add => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/addfriends/{userId}/{userFriendsId}")
    public ResponseEntity<UsersDTO> addFriends(@PathVariable("userId") Long userId,
                                              @PathVariable("userFriendsId") Long userFriendsId) {
        try {
            ResponseEntity resp = ResponseEntity.status(HttpStatus.CREATED).body(UsersMapper.INSTANCE.convertUsersToUsersDTO(usersService.addFriends(userId, userFriendsId)));
            //logger.info("/users/add friends => ok");
            return resp;

        } catch (Exception e) {
            //logger.error("/users/add friends => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/removefriends/{userId}/{userFriendsId}")
    public ResponseEntity<UsersDTO> removeFriends(@PathVariable("userId") Long userId,
                                              @PathVariable("userFriendsId") Long userFriendsId) {
        try {
            ResponseEntity resp = ResponseEntity.status(HttpStatus.CREATED).body(usersService.removeFriends(userId, userFriendsId));
            //logger.info("/users/remove friends => ok");
            return resp;

        } catch (Exception e) {
            //logger.error("/users/remove friends => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
