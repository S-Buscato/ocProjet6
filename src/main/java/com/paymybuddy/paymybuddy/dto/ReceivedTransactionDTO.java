package com.paymybuddy.paymybuddy.dto;

import com.paymybuddy.paymybuddy.models.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReceivedTransactionDTO {
    private UsersFriendsDTO emmeter;
    private double amount;
    private String description;

}
