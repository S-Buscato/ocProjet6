package com.paymybuddy.paymybuddy.service;
import com.paymybuddy.paymybuddy.constant.Fee;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.security.UserDetailsImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtilsService {

    @Autowired
    UsersService usersService;

    static Logger logger = Logger.getLogger(UtilsService.class);


    @Transactional
    public Users findCurrentUser()  {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl)loggedInUser.getPrincipal();
        logger.info("findCurrentUser");
        return usersService.findByEmail(user.getEmail()).get();
    }
}
