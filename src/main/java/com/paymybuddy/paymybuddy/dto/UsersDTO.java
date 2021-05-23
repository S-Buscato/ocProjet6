package com.paymybuddy.paymybuddy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class UsersDTO {
    private String firstName;
    private String lastName;
    private String email;
    private double totalAmount;
    private List<UsersFriendsDTO> friends;
    private List<ReceivedTransactionDTO> ReceivedTransactions;
    private List<EmmetedTransactionDTO> EmmetedTransactions;
    private List<BankAccountDTO> bankAccounts;
    private List<TransfertDTO> transferts;
    //private List<TransfertFromBankDTO> transfertFromBankDTOS;

}