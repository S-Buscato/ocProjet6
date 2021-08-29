package com.paymybuddy.paymybuddy.dto;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Generated
public class BankAccountDTO {
    private String description;
    private String iban;
    private String bankName;
    private boolean actif;
}
