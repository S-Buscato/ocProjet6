package com.paymybuddy.paymybuddy.exception;

import com.paymybuddy.paymybuddy.constant.Messages;

public class UserAllReadyExistException extends Exception {
    public UserAllReadyExistException(){
        super(Messages.USER_ALREADY_IN_YOUR_FRIENDS_LIST);
    }
}
