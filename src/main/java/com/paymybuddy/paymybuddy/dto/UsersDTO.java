package com.paymybuddy.paymybuddy.dto;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Generated
public class UsersDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private double totalAmount;
    private List<UsersMinimalsInfoDTO> friends;
    private List<ReceivedTransactionDTO> ReceivedTransactions;
    private List<EmmetedTransactionDTO> EmmetedTransactions;
    private List<BankAccountDTO> bankAccounts;
    private List<TransfertDTO> transferts;
}