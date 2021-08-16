package com.paymybuddy.paymybuddy.security;

import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.service.UsersService;
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

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email.trim().isEmpty()) {
            throw new UsernameNotFoundException("username is empty");
        }

        Users user = userService.findByEmail(email).get();

        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " not found");
        }

        return UserDetailsImpl.build(user);
        //return new org.springframework.security.core.userdetails.User(user.getFirstName(), user.getPassword(), getGrantedAuthorities(user));
    }

    public UserDetails getCurrentUser(String email) throws UsernameNotFoundException {
        if (email.trim().isEmpty()) {
            throw new UsernameNotFoundException("username is empty");
        }

        Users user = userService.findByEmail(email).get();

        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " not found");
        }

        return UserDetailsImpl.build(user);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Users user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        return Collections.emptyList();
    }
}
