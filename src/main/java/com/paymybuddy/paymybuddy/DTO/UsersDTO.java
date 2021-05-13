package com.paymybuddy.paymybuddy.DTO;

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
    private String password;
    private double totalAmount;
    private List<UsersFriendsDTO> friends;
}