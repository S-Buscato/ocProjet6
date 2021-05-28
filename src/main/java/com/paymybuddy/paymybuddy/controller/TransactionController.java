package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.EmmetedTransactionDTO;
import com.paymybuddy.paymybuddy.dto.RequestTransactionDTO;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.UserNotInFriendsListException;
import com.paymybuddy.paymybuddy.service.TransactionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymybody")
public class TransactionController {


    @Autowired
    TransactionService transactionService;

    static Logger logger = Logger.getLogger(TransactionController.class);


    @PostMapping("/users/sendMoney/{userId}")
    public ResponseEntity<EmmetedTransactionDTO> removeFriends(@PathVariable("userId") Long userId, @RequestBody RequestTransactionDTO requestTransactionDTO) {
        try {
            ResponseEntity resp = ResponseEntity.status(HttpStatus.CREATED).body(transactionService.sendMoneyToFriends(userId, requestTransactionDTO));
            logger.info("/users/sendMoney => ok");
            return resp;
        }
        catch (UserNotInFriendsListException e) {
            logger.error("users/sendMoney/ => Transaction impossible : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (InsuffisientBalanceException e) {
            logger.error("users/sendMoney/ => Transaction impossible : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            logger.error("/users/sendMoney => error : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO getALL
}
