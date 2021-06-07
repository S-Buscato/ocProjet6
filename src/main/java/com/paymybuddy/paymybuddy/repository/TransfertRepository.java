package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.models.BankAccount;
import com.paymybuddy.paymybuddy.models.Transfert;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransfertRepository extends CrudRepository<Transfert, Long> {
    List<Transfert> findAllByUsersId(long anyLong);

}
