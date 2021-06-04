package com.paymybuddy.paymybuddy.exception;

import com.paymybuddy.paymybuddy.constant.Messages;

public class UsersNotInFriendsListException extends Exception{
    public UsersNotInFriendsListException(){
        super(Messages.USER_NOT_IN_YOUR_FRIENDSLIST);
    }
}
