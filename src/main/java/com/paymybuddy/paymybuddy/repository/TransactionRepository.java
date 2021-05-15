package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.models.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
