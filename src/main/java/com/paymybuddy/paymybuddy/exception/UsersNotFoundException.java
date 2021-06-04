package com.paymybuddy.paymybuddy.exception;

import com.paymybuddy.paymybuddy.constant.Messages;

public class UsersNotFoundException extends Exception{

    public UsersNotFoundException(){
        super(Messages.USER_NOT_FOUND);
    }

}
