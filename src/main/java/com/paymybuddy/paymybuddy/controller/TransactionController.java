package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.RequestTransactionDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.mapper.UsersMapper;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.exception.UsersNotInFriendsListException;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UsersService;
import com.paymybuddy.paymybuddy.service.UtilsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/paymybuddy")
public class TransactionController {


    @Autowired
    TransactionService transactionService;

    @Autowired
    UsersService usersService;

    @Autowired
    UtilsService utilsService;


    static Logger logger = Logger.getLogger(TransactionController.class);

    @PostMapping(path="/users/transaction",  consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Object toSendMoney(@RequestParam("receiverEmail") String email) throws UsersNotFoundException {
        Users currentUser = utilsService.findCurrentUser();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
        modelAndView.addObject("receiver", usersService.findByEmail(email).get());
        modelAndView.setViewName("users/transaction");
        logger.info("toSendMoney " + email );
        return  modelAndView;
    }


    @PostMapping(path = "/users/sendMoney" ,  consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView sendMoney(@ModelAttribute("requestTransactionDTO") RequestTransactionDTO requestTransactionDTO) throws UsersNotFoundException {
        Users currentUser = utilsService.findCurrentUser();
        Users receiverUser = usersService.findByEmail(requestTransactionDTO.getReceiver().getEmail()).get();

        UsersMinimalsInfoDTO receiver = UsersMapper.INSTANCE.convertUsersToUsersFriendsDTO(receiverUser);
        requestTransactionDTO.setReceiver(receiver);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/transaction");
        modelAndView.addObject("receiver", usersService.findCurrentUserInfo(currentUser.getId()));
        try {
            transactionService.sendMoneyToFriends(currentUser.getId(), requestTransactionDTO);
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            modelAndView.addObject("receiver", usersService.findCurrentUserInfo(currentUser.getId()));
            logger.info("sendMoney to " + requestTransactionDTO.getReceiver().getFirstName() + " " + requestTransactionDTO.getReceiver().getLastName());
            return modelAndView;
        }
        catch (UsersNotInFriendsListException e) {
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            modelAndView.addObject("error", e.getMessage());
            logger.error("sendMoney to "+ requestTransactionDTO.getReceiver().getFirstName() + " " + requestTransactionDTO.getReceiver().getLastName() + " : " + e.getMessage());
            return modelAndView;
        }
        catch (InsuffisientBalanceException e) {
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            modelAndView.addObject("error", e.getMessage());
            logger.error("sendMoney to "+ requestTransactionDTO.getReceiver().getFirstName() + " " + requestTransactionDTO.getReceiver().getLastName() + " : " + e.getMessage());
            return modelAndView;
        }
        catch (Exception e) {
            modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
            modelAndView.addObject("error", e.getMessage());
            logger.error("sendMoney to "+ requestTransactionDTO.getReceiver().getFirstName() + " " + requestTransactionDTO.getReceiver().getLastName() + " : " + e.getMessage());
            return modelAndView;
        }
    }
}
