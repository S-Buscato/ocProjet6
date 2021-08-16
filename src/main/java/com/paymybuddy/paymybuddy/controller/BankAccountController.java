package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.BankAccountDTO;
import com.paymybuddy.paymybuddy.exception.BankAccountExistsException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.security.UserDetailsImpl;
import com.paymybuddy.paymybuddy.service.BankAccountService;
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
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    UsersService usersService;

    static Logger logger = Logger.getLogger(BankAccountController.class);

    @PostMapping(path="/bank/account",  consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Object banckAccount() throws UsersNotFoundException {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl  user = (UserDetailsImpl)loggedInUser.getPrincipal();
        Users currentUser = usersService.findByEmail(user.getEmail()).get();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("currentUser", usersService.findCurrentUserInfo(currentUser.getId()));
        modelAndView.setViewName("users/bankAccount");
        return  modelAndView;
    }


    @PostMapping("/bank/add")
    public ResponseEntity<BankAccountDTO> addBankAccount(@ModelAttribute("bankAccountDTO") @RequestBody BankAccountDTO bankAccountDTO) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl user = (UserDetailsImpl)loggedInUser.getPrincipal();
            Users currentUser = usersService.findByEmail(user.getEmail()).get();

            ResponseEntity response = ResponseEntity.status(HttpStatus.CREATED).body(bankAccountService.save(bankAccountDTO, currentUser.getId()));
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
