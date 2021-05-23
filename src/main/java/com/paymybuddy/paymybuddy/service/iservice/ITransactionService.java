package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.dto.ReceivedTransactionDTO;

public interface ITransactionService {
    ReceivedTransactionDTO sendMoneyToFriends(Long userId, Long usersFriendId, double amount);
}
