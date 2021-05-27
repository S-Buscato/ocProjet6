package com.paymybuddy.paymybuddy.exception;

import com.paymybuddy.paymybuddy.constant.Messages;

public class ExistingEmailException extends Exception {

    public ExistingEmailException(){
        super(Messages.EMAIL_EXIST);
    }
}
