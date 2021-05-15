package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.models.Transaction;

public interface ITransactionService {
    Transaction sendMoneyToFriends(Long userId, Long usersFriendId, double amount);
}
