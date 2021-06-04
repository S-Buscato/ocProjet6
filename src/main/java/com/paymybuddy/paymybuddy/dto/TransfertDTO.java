package com.paymybuddy.paymybuddy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TransfertDTO {
    private Date date;
    private Double amount;
    private BankAccountDTO bankAccount;
    private String transfertType;
}
