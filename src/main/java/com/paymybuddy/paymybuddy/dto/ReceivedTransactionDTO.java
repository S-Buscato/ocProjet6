package com.paymybuddy.paymybuddy.dto;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Generated
public class ReceivedTransactionDTO {
    private UsersMinimalsInfoDTO emmeter;
    private double amount;
    private String description;

}
