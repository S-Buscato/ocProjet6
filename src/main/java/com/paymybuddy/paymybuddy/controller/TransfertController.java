package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.constant.TransfertType;
import com.paymybuddy.paymybuddy.dto.BankTransfertDTO;
import com.paymybuddy.paymybuddy.exception.BadTransfertTypeException;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.NotActiveBankAccountException;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.service.TransfertService;
import com.paymybuddy.paymybuddy.service.UtilsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/paymybuddy")
public class TransfertController {

    @Autowired
    TransfertService transfertService;

    @Autowired
    UtilsService utilsService;

    static Logger logger = Logger.getLogger(TransfertController.class);

    @PostMapping("/transfertFromBank")
    public ModelAndView receiveMoneyFromBankAccount(@ModelAttribute("bankTransfertDTO") BankTransfertDTO bankTransfertDTO){
        bankTransfertDTO.setTransfertType(TransfertType.VIR_FROM_BANK_ACCOUNT.toString());
        Users currentUser = utilsService.findCurrentUser();
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/paymybuddy/myprofil", true));
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.setViewName("users/main");
        try {
            transfertService.receiveMoneyFromBankAccount(bankTransfertDTO);
            logger.info("receiveMoneyFromBankAccount : " + bankTransfertDTO.getAmount());
            return modelAndView;
        }
        catch (NotActiveBankAccountException e) {
            logger.error("receiveMoneyFromBankAccount : " + bankTransfertDTO.getAmount() + " : " + e.getMessage());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
        catch (BadTransfertTypeException e) {
            logger.error("receiveMoneyFromBankAccount : " + bankTransfertDTO.getAmount() + " : " + e.getMessage());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
        catch (Exception e) {
            logger.error("receiveMoneyFromBankAccount : " + bankTransfertDTO.getAmount() + " : " + e.getMessage());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;        }
    }

    @PostMapping("/transfertToBank")
    public ModelAndView sendMoneyToBankAccount(@ModelAttribute("bankTransfertDTO") BankTransfertDTO bankTransfertDTO){
        Users currentUser = utilsService.findCurrentUser();
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/paymybuddy/myprofil", true));
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.setViewName("users/main");
        try {
            bankTransfertDTO.setTransfertType(TransfertType.VIR_TO_BANK_ACCOUNT.toString());
            transfertService.sendMoneyToBankAccount(bankTransfertDTO);
            return modelAndView;
        }
        catch (NotActiveBankAccountException e) {
            logger.error("sendMoneyToBankAccount : " + bankTransfertDTO.getAmount() + " : " + e.getMessage());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;        }
        catch (BadTransfertTypeException e) {
            logger.error("sendMoneyToBankAccount : " + bankTransfertDTO.getAmount() + " : " + e.getMessage());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;        }
        catch (InsuffisientBalanceException e) {
            logger.error("sendMoneyToBankAccount : " + bankTransfertDTO.getAmount() + " : " + e.getMessage());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;        }
        catch (Exception e) {
            logger.error("sendMoneyToBankAccount : " + bankTransfertDTO.getAmount() + " : " + e.getMessage());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;        }
    }

}
