package com.paymybuddy.paymybuddy.exception;

import com.paymybuddy.paymybuddy.constant.Messages;

public class BadTransfertTypeException extends Exception {

    public BadTransfertTypeException(){
        super(Messages.BAD_TRANSFERT_TYPE);
    }
}
