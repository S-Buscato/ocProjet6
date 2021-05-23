package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.BankAccountDTO;
import com.paymybuddy.paymybuddy.dto.Mapper.BankAccountMapper;
import com.paymybuddy.paymybuddy.models.BankAccount;
import com.paymybuddy.paymybuddy.repository.BankAccountRepository;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.iservice.IBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService implements IBankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UsersRepository usersRepository;


    @Override
    public BankAccountDTO save(BankAccount bankAccount, Long usersId) {
        bankAccount.setUsers(usersRepository.findById(usersId).get());
        BankAccountDTO bankAccountDTO = BankAccountMapper.INSTANCE.convertBankAccountToBankAccountDTO(bankAccountRepository.save(bankAccount));
        return bankAccountDTO;
    }
}
