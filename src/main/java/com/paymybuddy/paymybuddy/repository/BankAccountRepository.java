package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.models.BankAccount;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

    BankAccount findByIban(String Iban);
}
