package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.BankAccountDTO;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.mapper.BankAccountMapper;
import com.paymybuddy.paymybuddy.mapper.UsersMapper;
import com.paymybuddy.paymybuddy.exception.BankAccountExistsException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.models.BankAccount;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.service.BankAccountService;
import com.paymybuddy.paymybuddy.service.UsersService;
import com.paymybuddy.paymybuddy.service.UtilsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/paymybuddy")
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    UsersService usersService;

    @Autowired
    UtilsService utilsService;

    static Logger logger = Logger.getLogger(BankAccountController.class);

    @GetMapping("/bank/account")
    public Object banckAccount() throws UsersNotFoundException {
        Users currentUser = utilsService.findCurrentUser();
        UsersDTO usersDTOInfo = UsersMapper.INSTANCE.convertUsersToUsersDTO(currentUser);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
        modelAndView.addObject("usersInfo", usersDTOInfo);
        logger.info("banckAccount()");
        modelAndView.setViewName("users/bankAccount");
        return  modelAndView;
    }


    @PostMapping(path="/bank/add")
    public ModelAndView addBankAccount(@ModelAttribute("bankAccountDTO") BankAccountDTO bankAccountDTO) {
        Users currentUser = utilsService.findCurrentUser();
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/paymybuddy/bank/account", true));
        modelAndView.setViewName("users/bankAccount");
        modelAndView.addObject("currentUser", currentUser);
        try {
            BankAccountDTO bankAccountAdded =  bankAccountService.save(bankAccountDTO, currentUser.getId());
            UsersDTO usersDTOInfo = UsersMapper.INSTANCE.convertUsersToUsersDTO(currentUser);
            usersDTOInfo.getBankAccounts().add(bankAccountAdded);
            modelAndView.addObject("usersInfo", usersDTOInfo);
            logger.info("addBankAccount() success : "+ bankAccountDTO.getIban());
            return  modelAndView;
        }
        catch (BankAccountExistsException e) {
            logger.error("addBankAccount() " + e.getMessage() + " " + bankAccountDTO.getIban());
            modelAndView.addObject("usersInfo", UsersMapper.INSTANCE.convertUsersToUsersDTO(currentUser));
            modelAndView.addObject("error", e.getMessage());
            return  modelAndView;
        }
        catch (Exception e) {
            logger.error("addBankAccount() " + e.getMessage() + " " + bankAccountDTO.getIban());
            return  modelAndView;
        }
    }

    @PostMapping("/bank/toggleActif")
    public ModelAndView toggleActif(@ModelAttribute("bankAccountDTO") BankAccountDTO bankAccountDTO) throws UsersNotFoundException {
        Users currentUser = utilsService.findCurrentUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
        modelAndView.setViewName("users/bankAccount");
        try {
            bankAccountService.toggleActiveBankAccount(bankAccountDTO);
            UsersDTO usersDTOInfo = UsersMapper.INSTANCE.convertUsersToUsersDTO(currentUser);
            modelAndView.addObject("usersInfo", usersDTOInfo);
            logger.info("toggleActif() " + bankAccountDTO.isActif());
            return  modelAndView;
        }
        catch (Exception e) {
            logger.error("toggleActif() " + e.getMessage());
            UsersDTO usersDTOInfo = UsersMapper.INSTANCE.convertUsersToUsersDTO(currentUser);
            modelAndView.addObject("usersInfo", usersDTOInfo);
            modelAndView.addObject("error", e.getMessage());
            return  modelAndView;
        }
    }
}
