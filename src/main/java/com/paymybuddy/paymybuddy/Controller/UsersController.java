package com.paymybuddy.paymybuddy.Controller;

import com.paymybuddy.paymybuddy.DTO.Mapper.UsersMapper;
import com.paymybuddy.paymybuddy.DTO.UsersDTO;
import com.paymybuddy.paymybuddy.DTO.UsersFriendsDTO;
import com.paymybuddy.paymybuddy.Models.Users;
import com.paymybuddy.paymybuddy.Service.IServices.UsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
            logger.info("api/users (getAll) => ok");
            return resp;
        } catch (Exception e) {
            logger.error("api/users (getAll) => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity findUsersFriends(@PathVariable Long id) {
        try {
            logger.info("api/users (getAll) => ok");
            ResponseEntity resp = ResponseEntity.ok(usersService.findUsersFriends(id));
            return resp;
        } catch (Exception e) {
            logger.error("api/users (getAll) => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/add")
    public ResponseEntity<UsersDTO> addPerson(@RequestBody UsersDTO usersDTO) {
        try {
            ResponseEntity resp = ResponseEntity.status(HttpStatus.CREATED).body(usersService.save(usersDTO));
            logger.info("api/users/add => ok");
            return resp;

        } catch (Exception e) {
            logger.error("api/users/add => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
