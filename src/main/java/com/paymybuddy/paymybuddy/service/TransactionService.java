package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.constant.Fee;
import com.paymybuddy.paymybuddy.models.Transaction;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.iservice.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public Transaction sendMoneyToFriends(Long userId, Long usersFriendId, double amount) {
        Double fee = amount * Fee.FEE_RATE;
        Double totalAmountTransfert = amount + fee;

        Users users = usersRepository.findById(userId).get();
        Users userFriends = usersRepository.findById(usersFriendId).get();

        Boolean friends = users.getFriends().contains(userFriends);
        Boolean amountOk = users.getTotalAmount() >= totalAmountTransfert;

        Transaction transaction = new Transaction();
        if(friends && amountOk){
            users.setTotalAmount(users.getTotalAmount() - totalAmountTransfert);
            userFriends.setTotalAmount(userFriends.getTotalAmount() + amount);
            transaction.setAmount(amount);
            transaction.setFee(fee);
            transaction.setEmmeter(users);
            transaction.setReceiver(userFriends);
            transaction.setDescription("test transaction");
            usersRepository.save(userFriends);
            usersRepository.save(users);
        }
        return  transactionRepository.save(transaction);
    }
}
