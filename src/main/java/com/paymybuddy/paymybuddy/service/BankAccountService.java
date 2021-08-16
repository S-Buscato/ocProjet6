package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.BankAccountDTO;
import com.paymybuddy.paymybuddy.dto.mapper.BankAccountMapper;
import com.paymybuddy.paymybuddy.exception.BankAccountExistsException;
import com.paymybuddy.paymybuddy.models.BankAccount;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.BankAccountRepository;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.iservice.IBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankAccountService implements IBankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UsersRepository usersRepository;


    @Override
    @Transactional
    public BankAccountDTO save(BankAccountDTO bankAccountDTO, Long usersId) throws BankAccountExistsException {
        if(bankAccountRepository.findByIban(bankAccountDTO.getIban()) != null){
            throw new BankAccountExistsException();
        }
        BankAccount bankAccount = BankAccountMapper.INSTANCE.convertBankAccountDTOToBankAccount(bankAccountDTO);
        bankAccount.setUsers(usersRepository.findById(usersId).get());
        return BankAccountMapper.INSTANCE.convertBankAccountToBankAccountDTO(bankAccountRepository.save(bankAccount));
    }

    @Override
    @Transactional
    public List<BankAccountDTO> findAll(Long id) {
        Users users = usersRepository.findById(id).get();
        return BankAccountMapper.INSTANCE.convertBankAccounListtToBankAccountDTOList(users.getBankAccounts());
    }

    @Override
    @Transactional
    public BankAccountDTO findById(long id) {
        return BankAccountMapper.INSTANCE.convertBankAccountToBankAccountDTO(bankAccountRepository.findById(id).get());
    }

    @Override
    @Transactional
    public BankAccountDTO findByIban(String iban) {
        return BankAccountMapper.INSTANCE.convertBankAccountToBankAccountDTO(bankAccountRepository.findByIban(iban));
    }

    @Override
    @Transactional
    public BankAccountDTO update(Long id, BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = bankAccountRepository.findById(id).get();
        bankAccount.setBankName(bankAccount.getBankName());
        bankAccount.setDescription(bankAccountDTO.getDescription());
        bankAccount.setIban(bankAccountDTO.getIban());
        return BankAccountMapper.INSTANCE.convertBankAccountToBankAccountDTO(bankAccountRepository.save(bankAccount));

    }

    @Override
    @Transactional
    public Long deleteById(long id) {
        try{
            bankAccountRepository.deleteById(id);
            return id;
        }catch (Exception e){
            throw e;
        }

    }

    public BankAccountDTO toggleActiveBankAccount(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = bankAccountRepository.findByIban(bankAccountDTO.getIban());
        bankAccount.setActif(!bankAccount.isActif());
        return BankAccountMapper.INSTANCE.convertBankAccountToBankAccountDTO(bankAccountRepository.save(bankAccount));
    }
}
