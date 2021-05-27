package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.BankTransfertDTO;
import com.paymybuddy.paymybuddy.dto.TransfertDTO;
import com.paymybuddy.paymybuddy.exception.BadTransfertTypeException;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.NotActiveBankAccountException;
import com.paymybuddy.paymybuddy.service.TransfertService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransfertController {

    @Autowired
    TransfertService transfertService;

    static Logger logger = Logger.getLogger(TransfertController.class);

    @PostMapping("/transfertFromBank/")
    public ResponseEntity<TransfertDTO> receiveMoneyFromBankAccount(@RequestBody BankTransfertDTO bankTransfertDTO){
        try {
            ResponseEntity response = ResponseEntity.status(HttpStatus.ACCEPTED).body(transfertService.receiveMoneyFromBankAccount(bankTransfertDTO));
            logger.info("/transfertFromBank");
            return response;
        }
        catch (NotActiveBankAccountException e) {
            logger.error("/transfertFromBank => Transfert impossible : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (BadTransfertTypeException e) {
            logger.error("/transfertFromBank => Transfert impossible : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            logger.error("/transfertFromBank => error : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/transfertToBank")
    public ResponseEntity<TransfertDTO> sendMoneyToBankAccount(@RequestBody BankTransfertDTO bankTransfertDTO){
        try {
            ResponseEntity response = ResponseEntity.status(HttpStatus.ACCEPTED).body(transfertService.sendMoneyFromBankAccount(bankTransfertDTO));
            logger.info("/transfertToBank ");
            return response;
        }
        catch (NotActiveBankAccountException e) {
            logger.error("/transfertToBank => error : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (BadTransfertTypeException e) {
            logger.error("/transfertFromBank => error : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (InsuffisientBalanceException e) {
            logger.error("/transfertToBank => error : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            logger.error("/transfertToBank => error : " + e);
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
