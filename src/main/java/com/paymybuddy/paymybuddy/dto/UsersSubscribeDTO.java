package com.paymybuddy.paymybuddy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersSubscribeDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private double amount = 0.0;
}
