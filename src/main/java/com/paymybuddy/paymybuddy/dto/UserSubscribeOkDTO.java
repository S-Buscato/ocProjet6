package com.paymybuddy.paymybuddy.dto;


import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Generated
public class UserSubscribeOkDTO {
    private String firstName;
    private String lastName;
    private String email;
    private double amount = 0.0;
}
