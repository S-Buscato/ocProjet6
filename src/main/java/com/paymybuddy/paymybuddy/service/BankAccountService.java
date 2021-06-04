package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.BankAccountDTO;
import com.paymybuddy.paymybuddy.dto.mapper.BankAccountMapper;
import com.paymybuddy.paymybuddy.exception.BankAccountExistsException;
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
    public BankAccountDTO save(BankAccountDTO bankAccountDTO, Long usersId) throws BankAccountExistsException {
        if(bankAccountRepository.findByIban(bankAccountDTO.getIban()) != null){
            throw new BankAccountExistsException();
        }
        BankAccount bankAccount = BankAccountMapper.INSTANCE.convertBankAccountDTOToBankAccount(bankAccountDTO);
        bankAccount.setUsers(usersRepository.findById(usersId).get());
        return BankAccountMapper.INSTANCE.convertBankAccountToBankAccountDTO(bankAccountRepository.save(bankAccount));
    }
}
