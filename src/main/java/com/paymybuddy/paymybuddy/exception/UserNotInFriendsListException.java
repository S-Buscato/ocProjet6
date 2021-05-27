package com.paymybuddy.paymybuddy.exception;

import com.paymybuddy.paymybuddy.constant.Messages;

public class UserNotInFriendsListException extends Exception{
    public UserNotInFriendsListException(){
        super(Messages.USER_NOT_IN_YOUR_FRIENDSLIST);
    }
}
