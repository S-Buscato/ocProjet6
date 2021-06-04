package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.dto.EmmetedTransactionDTO;
import com.paymybuddy.paymybuddy.dto.RequestTransactionDTO;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.UsersNotInFriendsListException;

public interface ITransactionService {

    EmmetedTransactionDTO sendMoneyToFriends(Long userId, RequestTransactionDTO requestTransactionDTO) throws InsuffisientBalanceException, UsersNotInFriendsListException;
}
