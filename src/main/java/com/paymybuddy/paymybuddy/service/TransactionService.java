package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.constant.Fee;
import com.paymybuddy.paymybuddy.dto.EmmetedTransactionDTO;
import com.paymybuddy.paymybuddy.dto.RequestTransactionDTO;
import com.paymybuddy.paymybuddy.dto.mapper.EmmetedTransactionMapper;
import com.paymybuddy.paymybuddy.dto.mapper.RequestTransactionDTOMapper;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.UsersNotInFriendsListException;
import com.paymybuddy.paymybuddy.models.Transaction;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.iservice.ITransactionService;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UsersRepository usersRepository;


    @Override
    public EmmetedTransactionDTO sendMoneyToFriends(Long userId, RequestTransactionDTO requestTransactionDTO)
            throws InsuffisientBalanceException, UsersNotInFriendsListException {

        Double fee = DoubleRounder.round(requestTransactionDTO.getAmount() * Fee.FEE_RATE,2);
        Double amount = DoubleRounder.round(requestTransactionDTO.getAmount(), 2);
        Double totalAmountTransfert =  DoubleRounder.round(amount + fee,2);

        Users users = usersRepository.findById(userId).get();
        Users userFriends = usersRepository.findByEmail(requestTransactionDTO.getReceiver().getEmail()).get();

        Boolean isAfriend = users.getFriends().contains(userFriends);
        Boolean isSuffisientBalance = users.getTotalAmount() >= totalAmountTransfert;

        if(!isSuffisientBalance){
            throw new InsuffisientBalanceException();
        }
        if(!isAfriend){
            throw  new UsersNotInFriendsListException();
        }

        Transaction transaction = RequestTransactionDTOMapper.INSTANCE.convertTransactionDTOToTransaction(requestTransactionDTO);
        EmmetedTransactionDTO emmetedTransactionDTO;

        users.setTotalAmount( DoubleRounder.round(users.getTotalAmount() - totalAmountTransfert,2) );
        userFriends.setTotalAmount(userFriends.getTotalAmount() != null? userFriends.getTotalAmount() + amount : amount);
        transaction.setFee(fee);
        transaction.setEmmeter(users);
        transaction.setReceiver(userFriends);

        try{
            usersRepository.save(userFriends);
            usersRepository.save(users);
            emmetedTransactionDTO  = EmmetedTransactionMapper.INSTANCE.convertTransactionToTransactionDTO(transactionRepository.save(transaction));
            emmetedTransactionDTO.setTotalAmount(totalAmountTransfert);
        }catch (Exception e){
            throw e;
        }
        return emmetedTransactionDTO;
    }
}
