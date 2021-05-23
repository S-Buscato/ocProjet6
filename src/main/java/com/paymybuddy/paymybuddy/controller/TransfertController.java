package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.TransfertDTO;
import com.paymybuddy.paymybuddy.dto.UserTransfertMoneyDTO;
import com.paymybuddy.paymybuddy.models.BankAccount;
import com.paymybuddy.paymybuddy.models.Transfert;
import com.paymybuddy.paymybuddy.service.TransfertService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransfertController {

    @Autowired
    TransfertService transfertService;

    static Logger logger = Logger.getLogger(TransfertController.class);

    @PostMapping("/transfert/")
    public ResponseEntity<Object> receive(@RequestBody UserTransfertMoneyDTO userTransfertMoneyDTO){
        ResponseEntity<Object> response;
        try {
            response = new ResponseEntity<Object>(transfertService.receiveMoneyFromBankAccount(userTransfertMoneyDTO),HttpStatus.CREATED);
            logger.info("/transfert/add "  );
            return response;

        } catch (Exception e) {
            logger.error("/transfert/add => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  /*  @PostMapping("/transfert/send/{userId}/{bankAccount}/{amount}")
    public ResponseEntity<Object> send(@PathVariable Long userId,
                                            @PathVariable Long bankAccount,
                                            @PathVariable double amount) {
        ResponseEntity<Object> response;
        try {
            response = new ResponseEntity<Object>(transfertService.sendMoneyToBankAccount(userId, bankAccount, amount),HttpStatus.CREATED);
            logger.info("/transfert/add => ok");
            return response;

        } catch (Exception e) {
            logger.error("/transfert/add => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

}
