package com.paymybuddy.paymybuddy.dto;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Generated
public class EmmetedTransactionDTO {
    private UsersMinimalsInfoDTO receiver;
    private double amount;
    private double fee;
    private String description;
    private double totalAmount;
}
