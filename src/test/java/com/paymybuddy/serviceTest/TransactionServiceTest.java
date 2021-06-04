package com.paymybuddy.serviceTest;

import com.paymybuddy.paymybuddy.constant.Fee;
import com.paymybuddy.paymybuddy.dto.EmmetedTransactionDTO;
import com.paymybuddy.paymybuddy.dto.RequestTransactionDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.UsersNotInFriendsListException;
import com.paymybuddy.paymybuddy.models.Transaction;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.TransactionService;
import org.decimal4j.util.DoubleRounder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TransactionService.class)
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;

    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    UsersRepository usersRepository;

    Users users = new Users();
    Users users2 = new Users();
    EmmetedTransactionDTO emmetedTransactionDTO = new EmmetedTransactionDTO();
    RequestTransactionDTO requestTransactionDTO = new RequestTransactionDTO();
    UsersMinimalsInfoDTO usersMinimalsInfoDTO = new UsersMinimalsInfoDTO();
    Transaction transaction = new Transaction();


    @BeforeEach
    public void setUp() {
        users.setId(1);
        users.setFirstName("John");
        users.setLastName("Doe");
        users.setEmail("john@doe.mail");
        users.setTotalAmount(100.0);
        users.setFriends(new ArrayList<>());

        users2.setId(2);
        users2.setFirstName("Jack");
        users2.setLastName("Sparrow");
        users2.setEmail("jack@s.mail");
        users2.setTotalAmount(50.0);
        users2.setFriends(new ArrayList<>());

        usersMinimalsInfoDTO.setEmail(users.getEmail());
        usersMinimalsInfoDTO.setFirstName(users.getFirstName());
        usersMinimalsInfoDTO.setLastName(users.getLastName());

        requestTransactionDTO.setAmount(50);
        requestTransactionDTO.setDescription("sous-sous");
        requestTransactionDTO.setReceiver(usersMinimalsInfoDTO);
    }

    @Test
    @DisplayName("test sendMoneyToFriends Succes")
    void testSendMoneyToFriends() throws UsersNotInFriendsListException, InsuffisientBalanceException {


        Double fee = DoubleRounder.round(requestTransactionDTO.getAmount() * Fee.FEE_RATE,2);
        Double amount = DoubleRounder.round(requestTransactionDTO.getAmount(), 2);

        users.getFriends().add(users2);

        when(usersRepository.findById(anyLong())).thenReturn(Optional.ofNullable(users));
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(users2));
        when(usersRepository.save(users)).thenReturn(users);
        when(usersRepository.save(users2)).thenReturn(users2);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Double totalAmountTransfer = amount+fee;
        Double amountEmmeter = users.getTotalAmount()-totalAmountTransfer;
        Double amountReceiver = users2.getTotalAmount()+amount;

        emmetedTransactionDTO = transactionService.sendMoneyToFriends(1L, requestTransactionDTO);
        Assertions.assertEquals(totalAmountTransfer, emmetedTransactionDTO.getTotalAmount());
        Assertions.assertEquals(amountEmmeter, users.getTotalAmount());
        Assertions.assertEquals(amountReceiver, users2.getTotalAmount());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(usersRepository, times(1)).findById(anyLong());
        verify(usersRepository, times(1)).findByEmail(anyString());
        verify(usersRepository, times(2)).save(any(Users.class));
    }
}

