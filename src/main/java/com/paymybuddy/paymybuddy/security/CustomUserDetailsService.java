package com.paymybuddy.paymybuddy.security;

import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.service.UsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersService userService;

    static Logger logger = Logger.getLogger(CustomUserDetailsService.class);


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email.trim().isEmpty()) {
            logger.error("loadUserByUsername ");
            throw new UsernameNotFoundException("username is empty");
        }

        Users user = userService.findByEmail(email).get();

        if (user == null) {
            logger.error("loadUserByUsername : " + email);
            throw new UsernameNotFoundException("User " + email + " not found");
        }
        logger.info("loadUserByUsername : " + email);
        return UserDetailsImpl.build(user);
    }
}
