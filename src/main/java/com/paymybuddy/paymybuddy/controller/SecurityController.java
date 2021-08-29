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

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }


    @RequestMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    // Login form
    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/subscribe")
    public ModelAndView subscribe(@ModelAttribute("usersMinimalsInfoDTO") UsersSubscribeDTO usersSubscribeDTO) {
        try {
            ModelAndView modelAndView = new ModelAndView(new RedirectView("/login", true));
            modelAndView.setViewName("subscribe");

            UserSubscribeOkDTO userSubscribeOkDTO = usersService.subscribe(usersSubscribeDTO);
            modelAndView.addObject("userSubscribeOkDTO", userSubscribeOkDTO);
            logger.info("subscribe succes : " + usersSubscribeDTO);
            return modelAndView;
        }
        catch (ExistingEmailException e) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("subscribe");
            logger.error("subscribe => email existant : " + usersSubscribeDTO.getEmail());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
        catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("subscribe");
            logger.error("subscribe => error : " + e);
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
