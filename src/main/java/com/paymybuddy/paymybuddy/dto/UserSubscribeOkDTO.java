package com.paymybuddy.paymybuddy.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSubscribeOkDTO {
    private String firstName;
    private String lastName;
    private String email;
    private double amount = 0.0;
}
