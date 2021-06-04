package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.dto.BankTransfertDTO;
import com.paymybuddy.paymybuddy.dto.TransfertDTO;
import com.paymybuddy.paymybuddy.dto.UserTransfertMoneyDTO;
import com.paymybuddy.paymybuddy.exception.BadTransfertTypeException;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.NotActiveBankAccountException;

public interface ITransfertService {

    TransfertDTO receiveMoneyFromBankAccount(BankTransfertDTO bankTransfertDTO) throws NotActiveBankAccountException, BadTransfertTypeException;

    TransfertDTO sendMoneyFromBankAccount(BankTransfertDTO bankTransfertDTO) throws NotActiveBankAccountException, InsuffisientBalanceException, BadTransfertTypeException;
}
