package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.dto.EmmetedTransactionDTO;
import com.paymybuddy.paymybuddy.dto.ReceivedTransactionDTO;
import com.paymybuddy.paymybuddy.dto.RequestTransactionDTO;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.UsersNotInFriendsListException;

import java.util.List;

public interface ITransactionService {

    EmmetedTransactionDTO sendMoneyToFriends(Long userId, RequestTransactionDTO requestTransactionDTO) throws InsuffisientBalanceException, UsersNotInFriendsListException;

    List<EmmetedTransactionDTO> findByEmmeter(long emmeterId);

    List<ReceivedTransactionDTO> findbyReceiver(long receiverId);
}
