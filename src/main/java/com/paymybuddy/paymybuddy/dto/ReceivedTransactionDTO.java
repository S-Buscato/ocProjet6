package com.paymybuddy.paymybuddy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReceivedTransactionDTO {
    private UsersMinimalsInfoDTO emmeter;
    private double amount;
    private String description;

}
