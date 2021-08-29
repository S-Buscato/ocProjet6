package com.paymybuddy.paymybuddy.dto;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Generated
public class UserTransfertMoneyDTO {
    private Double amount;
    private String email;
    private String iban;
    private String description;
    private String transfertType;

}
