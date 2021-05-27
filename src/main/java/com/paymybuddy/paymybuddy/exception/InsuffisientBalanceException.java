package com.paymybuddy.paymybuddy.exception;

import com.paymybuddy.paymybuddy.constant.Messages;

public class InsuffisientBalanceException extends  Exception{
    public InsuffisientBalanceException(){
        super(Messages.INSUFFICIENT_BALANCE);
    }
}
