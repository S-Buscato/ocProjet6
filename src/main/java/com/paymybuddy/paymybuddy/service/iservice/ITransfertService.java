package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.dto.BankTransfertDTO;
import com.paymybuddy.paymybuddy.dto.TransfertDTO;
import com.paymybuddy.paymybuddy.exception.BadTransfertTypeException;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.NotActiveBankAccountException;
import com.paymybuddy.paymybuddy.models.Transfert;

import java.util.List;

public interface ITransfertService {

    TransfertDTO receiveMoneyFromBankAccount(BankTransfertDTO bankTransfertDTO) throws NotActiveBankAccountException, BadTransfertTypeException;

    TransfertDTO sendMoneyToBankAccount(BankTransfertDTO bankTransfertDTO) throws NotActiveBankAccountException, InsuffisientBalanceException, BadTransfertTypeException;

    List<Transfert> findAllByUsersId(long usersId);

    TransfertDTO findById(long id);
}
