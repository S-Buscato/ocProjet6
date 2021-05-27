package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.dto.BankAccountDTO;
import com.paymybuddy.paymybuddy.exception.BankAccountExistsException;
import com.paymybuddy.paymybuddy.models.BankAccount;

public interface IBankAccountService {
    BankAccountDTO save(BankAccountDTO bankAccountDTO, Long usersId) throws BankAccountExistsException;
}
