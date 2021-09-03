package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.UserSubscribeOkDTO;
import com.paymybuddy.paymybuddy.dto.UsersSubscribeDTO;
import com.paymybuddy.paymybuddy.exception.ExistingEmailException;
import com.paymybuddy.paymybuddy.service.UsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class SecurityController {
    static Logger logger = Logger.getLogger(SecurityController.class);


    @Autowired
    UsersService usersService;

    @RequestMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/register")
    public ModelAndView register(@ModelAttribute("usersMinimalsInfoDTO") UsersSubscribeDTO usersSubscribeDTO) {
        try {
            ModelAndView modelAndView = new ModelAndView(new RedirectView("/login.html", true));
            modelAndView.setViewName("login");
            UserSubscribeOkDTO userSubscribeOkDTO = usersService.subscribe(usersSubscribeDTO);
            modelAndView.addObject("userSubscribeOkDTO", userSubscribeOkDTO);
            logger.info("resgister succes : " + usersSubscribeDTO);
            return modelAndView;
        }
        catch (ExistingEmailException e) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("register");
            logger.error("register => email existant : " + usersSubscribeDTO.getEmail());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
        catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("register");
            logger.error("register => error : " + e);
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
