package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.BankAccountDTO;
import com.paymybuddy.paymybuddy.exception.BankAccountExistsException;
import com.paymybuddy.paymybuddy.service.BankAccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymybody")
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    static Logger logger = Logger.getLogger(BankAccountController.class);


    @PostMapping("/bank/add/{id}")
    public ResponseEntity<BankAccountDTO> addBankAccount(@PathVariable Long id, @RequestBody BankAccountDTO bankAccountDTO) {
        try {
            ResponseEntity response = ResponseEntity.status(HttpStatus.CREATED).body(bankAccountService.save(bankAccountDTO, id));
            logger.info("/bank/add/{id}");
            return response;

        } catch (BankAccountExistsException e) {
            logger.error("/bank/add/{id} => ajout impossible : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            logger.error("/bank/add/{id} => error : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //TODO le rest des du REST
}
