package com.paymybuddy.paymybuddy.exception;

import com.paymybuddy.paymybuddy.constant.Messages;

public class BalanceException extends  Exception{
    public BalanceException(){
        super(Messages.TOTAL_AMOUNT);
    }
}
