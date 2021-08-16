package com.paymybuddy.paymybuddy.controller;


import com.paymybuddy.paymybuddy.dto.UserSubscribeOkDTO;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.dto.UsersSubscribeDTO;
import com.paymybuddy.paymybuddy.exception.ExistingEmailException;
import com.paymybuddy.paymybuddy.exception.UserAllReadyExistException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.security.CustomUserDetailsService;
import com.paymybuddy.paymybuddy.security.UserDetailsImpl;
import com.paymybuddy.paymybuddy.service.UsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    static Logger logger = Logger.getLogger(UsersController.class);


    @GetMapping("/paymybuddy/users")
    public Object findAllUsers(Model model) {
        try {
            ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(usersService.findall());
            logger.info("/users findAllUsers");
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl  user = (UserDetailsImpl)loggedInUser.getPrincipal();
            Users currentUser = usersService.findByEmail(user.getEmail()).get();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("users/main");
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            modelAndView.setViewName("users/list");
            modelAndView.addObject("users", usersService.findall());
            return modelAndView;
        }
        catch (Exception e) {
            logger.error("/users findAllUsers => error : " + e);
            return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            //return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("/paymybuddy/findUsersFriends/{id}")
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

    @GetMapping("/paymybuddy/search")
    public Object getUsersInfo(@ModelAttribute("usersMinimalsInfoDTO") UsersMinimalsInfoDTO usersMinimalsInfoDTO) throws UsersNotFoundException {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl  user = (UserDetailsImpl)loggedInUser.getPrincipal();
            Users currentUser = usersService.findByEmail(user.getEmail()).get();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("users/main");

            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            modelAndView.addObject("foundedUser", usersService.findUserInfo(usersMinimalsInfoDTO.getEmail()));
            return modelAndView;
           /* logger.info("/users/{id} getUsersInfo");
            ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(usersService.findUserInfo(id));
            return response;*/
        }
        catch (UsersNotFoundException e) {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl  user = (UserDetailsImpl)loggedInUser.getPrincipal();
            Users currentUser = usersService.findByEmail(user.getEmail()).get();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("users/main");
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
/*            logger.error("/users/{id} getUsersInfo => users not found : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);*/
        }
        catch (Exception e) {
            logger.error("/users/{id} getUsersInfo => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paymybuddy/myprofil")
    public Object myProfil() {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl  user = (UserDetailsImpl)loggedInUser.getPrincipal();
            Users currentUser = usersService.findByEmail(user.getEmail()).get();

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("users/main");

            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            logger.info("/paymybuddy/myprofil SUCCES");
            return modelAndView;
        } catch (Exception e) {
            logger.error("/paymybuddy/myprofil => error : " + e);
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

    @PostMapping(path="/paymybuddy/users/addfriends", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
            public Object addFriends(@RequestParam("email") String email) throws UsersNotFoundException {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl  user = (UserDetailsImpl)loggedInUser.getPrincipal();
            Users currentUser = usersService.findByEmail(user.getEmail()).get();

            ModelAndView modelAndView = new ModelAndView(new RedirectView("/paymybuddy/myprofil.html", true));
            modelAndView.setViewName("users/main");

            modelAndView.addObject("addedUser", usersService.addFriends(currentUser.getId(), email));
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            //ResponseEntity response = ResponseEntity.status(HttpStatus.ACCEPTED).body(usersService.addFriends(currentUser.getId(), email));
            //modelAndView.addObject(response);
            logger.info("/users/addfriends/{userId}");
            return modelAndView;

        }
        catch (UsersNotFoundException e) {
            logger.error("/users/addfriends/{userId} => ajout impossible : " + e);
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl  user = (UserDetailsImpl)loggedInUser.getPrincipal();
            Users currentUser = usersService.findByEmail(user.getEmail()).get();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("users/main");
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            modelAndView.addObject("error", HttpStatus.NOT_FOUND.toString());
            return modelAndView;
        }
        catch (UserAllReadyExistException e) {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl  user = (UserDetailsImpl)loggedInUser.getPrincipal();
            Users currentUser = usersService.findByEmail(user.getEmail()).get();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("users/main");
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            modelAndView.addObject("error", HttpStatus.CONFLICT.toString());
            return modelAndView;
           /* logger.error("/users/addfriends/{userId} => ajout impossible : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);*/
        }
        catch (Exception e) {
            logger.error("/users/addfriends/{userId} => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/paymybuddy/users/removefriends", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Object removeFriends(@RequestParam("removeUserEmail") String email) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl  user = (UserDetailsImpl)loggedInUser.getPrincipal();
            Users currentUser = usersService.findByEmail(user.getEmail()).get();

            ModelAndView modelAndView = new ModelAndView( new RedirectView("/paymybuddy/myprofil.html", true));
            modelAndView.setViewName("users/main");

            modelAndView.addObject("removedUserUser", usersService.removeFriends(currentUser.getId(), email));
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            return modelAndView;

        /*    ResponseEntity response = ResponseEntity.status(HttpStatus.CREATED).body(usersService.removeFriends(userId, email));
            logger.info("/users/removefriends");
            return response;*/
        }
        catch (Exception e) {
            logger.error("/users/removefriends => error : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/paymybuddy/main")
    public Object subscribe(@RequestBody UsersDTO usersDTO) {
        try {

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("users/main");
            modelAndView.addObject("users", usersService.findCurrentUserInfo(1l));
            return modelAndView;
        } catch (Exception e) {
            logger.error("/subscribe => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
