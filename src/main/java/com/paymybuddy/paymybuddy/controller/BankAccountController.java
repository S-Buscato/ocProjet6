package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.BankAccountDTO;
import com.paymybuddy.paymybuddy.models.BankAccount;
import com.paymybuddy.paymybuddy.service.BankAccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    static Logger logger = Logger.getLogger(BankAccountController.class);


    @PostMapping("/bank/add/{id}")
    public ResponseEntity<BankAccountDTO> addPerson(@PathVariable Long id,
                                                    @RequestBody BankAccount bankAccount) {
        try {
            ResponseEntity resp = ResponseEntity.status(HttpStatus.CREATED).body(bankAccountService.save(bankAccount, id));
            logger.info("/bank/add => ok");
            return resp;

        } catch (Exception e) {
            logger.error("/bank/add => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
