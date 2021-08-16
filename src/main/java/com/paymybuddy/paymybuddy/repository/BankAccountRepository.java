package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.models.BankAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

    BankAccount findByIban(String Iban);
}
