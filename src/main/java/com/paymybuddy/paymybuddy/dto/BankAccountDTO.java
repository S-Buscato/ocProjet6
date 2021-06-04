package com.paymybuddy.paymybuddy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankAccountDTO {
    private String description;
    private String iban;
    private String bankName;
    private boolean actif;
}
