package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.constant.Fee;
import com.paymybuddy.paymybuddy.dto.EmmetedTransactionDTO;
import com.paymybuddy.paymybuddy.dto.ReceivedTransactionDTO;
import com.paymybuddy.paymybuddy.dto.RequestTransactionDTO;
import com.paymybuddy.paymybuddy.mapper.EmmetedTransactionMapper;
import com.paymybuddy.paymybuddy.mapper.ReceivedTransactionMapper;
import com.paymybuddy.paymybuddy.mapper.RequestTransactionDTOMapper;
import com.paymybuddy.paymybuddy.exception.BalanceException;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.UsersNotInFriendsListException;
import com.paymybuddy.paymybuddy.models.Transaction;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.iservice.ITransactionService;
import org.apache.log4j.Logger;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UsersRepository usersRepository;

    static Logger logger = Logger.getLogger(TransactionService.class);


    @Override
    public EmmetedTransactionDTO sendMoneyToFriends(Long userId, RequestTransactionDTO requestTransactionDTO)
            throws InsuffisientBalanceException, UsersNotInFriendsListException, BalanceException {

        Double fee = DoubleRounder.round(requestTransactionDTO.getAmount() * Fee.FEE_RATE,2);
        Double amount = DoubleRounder.round(requestTransactionDTO.getAmount(), 2);
        Double totalAmountTransfert =  DoubleRounder.round(amount + fee,2);

        Users users = usersRepository.findById(userId).get();
        Users userFriends = usersRepository.findByEmail(requestTransactionDTO.getReceiver().getEmail()).get();


        if(users.getTotalAmount() == null || users.getTotalAmount() == 0.0){
            logger.error("EmmetedTransactionDTO");
            throw new BalanceException();
        }

        Boolean isAfriend = users.getFriends().contains(userFriends);
        Boolean isSuffisientBalance = users.getTotalAmount() >= totalAmountTransfert;

        if(!isSuffisientBalance){
            logger.error("EmmetedTransactionDTO");
            throw new InsuffisientBalanceException();
        }
        if(!isAfriend){
            logger.error("EmmetedTransactionDTO");
            throw  new UsersNotInFriendsListException();
        }

        Transaction transaction = RequestTransactionDTOMapper.INSTANCE.convertTransactionDTOToTransaction(requestTransactionDTO);
        EmmetedTransactionDTO emmetedTransactionDTO;

        users.setTotalAmount(DoubleRounder.round(users.getTotalAmount() - totalAmountTransfert,2) );
        userFriends.setTotalAmount(userFriends.getTotalAmount() != null? userFriends.getTotalAmount() + amount : amount);
        transaction.setFee(fee);
        transaction.setEmmeter(users);
        transaction.setReceiver(userFriends);

        try{
            usersRepository.save(userFriends);
            usersRepository.save(users);
            logger.info("EmmetedTransactionDTO");
            emmetedTransactionDTO = EmmetedTransactionMapper.INSTANCE.convertTransactionToTransactionDTO(transactionRepository.save(transaction));
            emmetedTransactionDTO.setTotalAmount(totalAmountTransfert);
        }catch (Exception e){
            logger.error("EmmetedTransactionDTO");
            throw e;
        }
        return emmetedTransactionDTO;
    }

    @Override
    public List<EmmetedTransactionDTO> findByEmmeter(long emmeterId) {
        logger.info("findByEmmeter : " + emmeterId);
        return EmmetedTransactionMapper.INSTANCE.convertTransactionListToTransactionDTOList(transactionRepository.findTransactionByEmmeter(emmeterId));
    }

    @Override
    public List<ReceivedTransactionDTO> findbyReceiver(long receiverId) {
        logger.info("findbyReceiver : " + receiverId);
        return ReceivedTransactionMapper.INSTANCE.convertTransactionListToTransactionDTOList(transactionRepository.findTransactionByReceiver(receiverId));
    }
}
