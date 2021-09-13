package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.BankAccountDTO;
import com.paymybuddy.paymybuddy.mapper.BankAccountMapper;
import com.paymybuddy.paymybuddy.exception.BankAccountExistsException;
import com.paymybuddy.paymybuddy.models.BankAccount;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.BankAccountRepository;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.iservice.IBankAccountService;
import org.apache.log4j.Logger;
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

    static Logger logger = Logger.getLogger(BankAccountService.class);


    @Override
    @Transactional
    public BankAccountDTO save(BankAccountDTO bankAccountDTO, Long usersId) throws BankAccountExistsException {
        if(bankAccountRepository.findByIban(bankAccountDTO.getIban()) != null){
            logger.error("save");
            throw new BankAccountExistsException();
        }
        logger.info("save");
        BankAccount bankAccount = BankAccountMapper.INSTANCE.convertBankAccountDTOToBankAccount(bankAccountDTO);
        bankAccount.setUsers(usersRepository.findById(usersId).get());
        return BankAccountMapper.INSTANCE.convertBankAccountToBankAccountDTO(bankAccountRepository.save(bankAccount));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankAccountDTO> findAll(Long id) {
        logger.info("findAll");
        Users users = usersRepository.findById(id).get();
        return BankAccountMapper.INSTANCE.convertBankAccounListtToBankAccountDTOList(users.getBankAccounts());
    }

    @Override
    @Transactional(readOnly = true)
    public BankAccountDTO findById(long id) {
        logger.info("findById");
        return BankAccountMapper.INSTANCE.convertBankAccountToBankAccountDTO(bankAccountRepository.findById(id).get());
    }

    @Override
    @Transactional(readOnly = true)
    public BankAccountDTO findByIban(String iban) {
        logger.info("findByIban");
        return BankAccountMapper.INSTANCE.convertBankAccountToBankAccountDTO(bankAccountRepository.findByIban(iban));
    }

    @Override
    @Transactional
    public BankAccountDTO update(Long id, BankAccountDTO bankAccountDTO) {
        logger.info("update");

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
            logger.info("deleteById : " + id);
            bankAccountRepository.deleteById(id);
            return id;
        }catch (Exception e){
            logger.error("deleteById : " + id);
            throw e;
        }
    }

    public BankAccountDTO toggleActiveBankAccount(BankAccountDTO bankAccountDTO) {
        logger.info("toggleActiveBankAccount : " + bankAccountDTO.getIban());
        BankAccount bankAccount = bankAccountRepository.findByIban(bankAccountDTO.getIban());
        bankAccount.setActif(!bankAccount.isActif());
        return BankAccountMapper.INSTANCE.convertBankAccountToBankAccountDTO(bankAccountRepository.save(bankAccount));
    }
}
