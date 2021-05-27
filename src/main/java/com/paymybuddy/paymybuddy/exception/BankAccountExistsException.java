package com.paymybuddy.paymybuddy.exception;

import com.paymybuddy.paymybuddy.constant.Messages;

public class BankAccountExistsException extends Exception{
    public BankAccountExistsException(){
        super(Messages.BNK_ACCOUNT_ALLREADY_EXIST);
    }
}
