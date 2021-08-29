package com.paymybuddy.paymybuddy.controller;


import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.exception.UserAllReadyExistException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.security.CustomUserDetailsService;
import com.paymybuddy.paymybuddy.service.UsersService;
import com.paymybuddy.paymybuddy.service.UtilsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    UtilsService utilsService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    static Logger logger = Logger.getLogger(UsersController.class);


    @GetMapping("/paymybuddy/users")
    public ModelAndView findAllUsers() throws UsersNotFoundException {
        Users currentUser = utilsService.findCurrentUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/main");
        modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));

        try {
            modelAndView.addObject("users", usersService.findall());
            modelAndView.setViewName("users/list");
            logger.info("findAllUsers => OK");
            return modelAndView;
        }
        catch (Exception e) {
            modelAndView.addObject("error", e.getMessage());
            logger.error("findAllUsers => error : " + e);
            return modelAndView;
        }
    }

    @GetMapping("/paymybuddy/history")
    public ModelAndView findUsersInfo() throws UsersNotFoundException {
        Users currentUser = utilsService.findCurrentUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/history");
        modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
        try {
            logger.info("findUsersInfo : " + currentUser.getLastName() + " " + currentUser.getFirstName());
            return modelAndView;
        } catch (Exception e) {
            logger.error("findUsersInfo => error : " + e);
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/paymybuddy/search")
    public ModelAndView getUsersInfo(@ModelAttribute("usersMinimalsInfoDTO") UsersMinimalsInfoDTO usersMinimalsInfoDTO) throws UsersNotFoundException {
        Users currentUser = utilsService.findCurrentUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/main");
        modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
        try {
            modelAndView.addObject("foundedUser", usersService.findUserInfo(usersMinimalsInfoDTO.getEmail()));
            modelAndView.addObject("usersFriendsList", usersService.findUsersFriends(currentUser.getId()));
            logger.error("getUsersInfo => " + usersMinimalsInfoDTO.getEmail());
            return modelAndView;
        }
        catch (UsersNotFoundException e) {
            modelAndView.addObject("error", e.getMessage());
            logger.error("getUsersInfo => users not found : " + usersMinimalsInfoDTO.getEmail());
            return modelAndView;
        }
        catch (Exception e) {
            modelAndView.addObject("error", e.getMessage());
            logger.error("getUsersInfo => error : " + e);
            return modelAndView;
        }
    }

    @GetMapping("/paymybuddy/myprofil")
    public ModelAndView myProfil() {
        Users currentUser = utilsService.findCurrentUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/main");
        try {
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            modelAndView.addObject("usersFriendsList", usersService.findUsersFriends(currentUser.getId()));
            logger.info("myProfil SUCCES : " + currentUser.getFirstName() + " " + currentUser.getLastName() );
            return modelAndView;
        } catch (Exception e) {
            logger.error("myProfil => error : " + e);
            modelAndView.addObject("error", e.getMessage());
            return  modelAndView;
        }
    }



    @PostMapping(path="/paymybuddy/users/addfriends", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView addFriends(@RequestParam("email") String email) throws UsersNotFoundException {
        Users currentUser = utilsService.findCurrentUser();
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/paymybuddy/myprofil", true));
        modelAndView.setViewName("users/main");
        modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
        try {
            modelAndView.addObject("addedUser", usersService.addFriends(currentUser.getId(), email));
            modelAndView.addObject("usersFriendsList", usersService.findUsersFriends(currentUser.getId()));
            logger.info("addFriends : " + email);
            return modelAndView;

        }
        catch (UsersNotFoundException e) {
            modelAndView.addObject("error", e.getMessage());
            logger.error("addFriends => UserNotFound : " + email);
            return modelAndView;
        }
        catch (UserAllReadyExistException e) {
            modelAndView.addObject("error", e.getMessage());
            logger.error("addFriends => User All ready Exists : " + email);
            return modelAndView;
        }
        catch (Exception e) {
            modelAndView.addObject("error", e.getMessage());
            logger.error("addFriends => error : " + e);
            return modelAndView;
        }
    }

    @PostMapping(path="/paymybuddy/users/removefriends", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView removeFriends(@RequestParam("removeUserEmail") String email) {
        Users currentUser = utilsService.findCurrentUser();
        ModelAndView modelAndView = new ModelAndView( new RedirectView("/paymybuddy/myprofil.html", true));
        modelAndView.setViewName("users/main");
        try {
            modelAndView.addObject("removedUserUser", usersService.removeFriends(currentUser.getId(), email));
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            modelAndView.addObject("usersFriendsList", usersService.findUsersFriends(currentUser.getId()));

            logger.info("removeFriends : " + email);
            return modelAndView;
        }
        catch (UsersNotFoundException e) {
            modelAndView.addObject("error", e.getMessage());
            logger.error("removeFriends => UserNotFound : " + email);
            return modelAndView;
        }
        catch (Exception e) {
            logger.error("removeFriends => error : " + e);
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

}
