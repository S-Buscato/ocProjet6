package com.paymybuddy.paymybuddy.exception;

import com.paymybuddy.paymybuddy.constant.Messages;

public class NotActiveBankAccountException extends Exception{
    public NotActiveBankAccountException(){
        super(Messages.NOT_ACTIVE_BANK_ACCOUNT);
    }

}
