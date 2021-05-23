package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.ReceivedTransactionDTO;
import com.paymybuddy.paymybuddy.service.TransactionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {


    @Autowired
    TransactionService transactionService;

    static Logger logger = Logger.getLogger(TransactionController.class);


    @PostMapping("/users/sendMoney/{userId}/{userFriendsId}/{amount}")
    public ResponseEntity<ReceivedTransactionDTO> removeFriends(@PathVariable("userId") Long userId,
                                                                @PathVariable("userFriendsId") Long userFriendsId,
                                                                @PathVariable("amount") double amount) {
        try {
            ResponseEntity resp = ResponseEntity.status(HttpStatus.CREATED).body(transactionService.sendMoneyToFriends(userId, userFriendsId, amount));
            logger.info("/users/sendMoney => ok");
            return resp;

        } catch (Exception e) {
            logger.error("/users/sendMoney => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
