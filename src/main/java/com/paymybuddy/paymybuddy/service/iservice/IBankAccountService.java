package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.dto.BankAccountDTO;
import com.paymybuddy.paymybuddy.exception.BankAccountExistsException;

import java.util.List;

public interface IBankAccountService {
    BankAccountDTO save(BankAccountDTO bankAccountDTO, Long usersId) throws BankAccountExistsException;

    List<BankAccountDTO> findAll(Long id);

    BankAccountDTO findById(long id);

    BankAccountDTO findByIban(String iban);

    BankAccountDTO update(Long id, BankAccountDTO bankAccountDTO);

    Long deleteById(long id);
}
