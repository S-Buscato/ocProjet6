package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.EmmetedTransactionDTO;
import com.paymybuddy.paymybuddy.dto.RequestTransactionDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.dto.mapper.UsersMapper;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.exception.UsersNotInFriendsListException;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.security.UserDetailsImpl;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/paymybuddy")
public class TransactionController {


    @Autowired
    TransactionService transactionService;

    @Autowired
    UsersService usersService;

    static Logger logger = Logger.getLogger(TransactionController.class);

    @PostMapping(path="/users/transaction",  consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Object toSendMoney(@RequestParam("receiverEmail") String email) throws UsersNotFoundException {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl  user = (UserDetailsImpl)loggedInUser.getPrincipal();
        Users currentUser = usersService.findByEmail(user.getEmail()).get();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
        modelAndView.addObject("receiver", usersService.findByEmail(email).get());
        modelAndView.setViewName("users/transaction");
        return  modelAndView;
    }


    @PostMapping("/users/sendMoney")
    public Object sendMoney(@ModelAttribute("requestTransactionDTO") RequestTransactionDTO requestTransactionDTO) throws UsersNotFoundException {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl user = (UserDetailsImpl)loggedInUser.getPrincipal();
            Users currentUser = usersService.findByEmail(user.getEmail()).get();
            Users receiverUser = usersService.findByEmail(requestTransactionDTO.getReceiver().getEmail()).get();

            UsersMinimalsInfoDTO receiver = UsersMapper.INSTANCE.convertUsersToUsersFriendsDTO(receiverUser);
            requestTransactionDTO.setReceiver(receiver);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("users/transaction");

            transactionService.sendMoneyToFriends(currentUser.getId(), requestTransactionDTO);
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            modelAndView.addObject("receiver", usersService.findCurrentUserInfo(currentUser.getId()));


            ResponseEntity resp = ResponseEntity.status(HttpStatus.CREATED).body(transactionService.sendMoneyToFriends(currentUser.getId(), requestTransactionDTO));
            logger.info("/users/sendMoney => ok");
            return modelAndView;
        }
        catch (UsersNotInFriendsListException e) {
            logger.error("users/sendMoney/ => Transaction impossible : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (InsuffisientBalanceException e) {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl  user = (UserDetailsImpl)loggedInUser.getPrincipal();

            Users currentUser = usersService.findByEmail(user.getEmail()).get();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("users/main");
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));

            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
           /* logger.error("users/sendMoney/ => Transaction impossible : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);*/
        }
        catch (Exception e) {
            logger.error("/users/sendMoney => error : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO getALL
}
