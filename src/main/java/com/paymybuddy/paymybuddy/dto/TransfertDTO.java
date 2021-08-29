package com.paymybuddy.paymybuddy.dto;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Generated
public class TransfertDTO {
    private Date date;
    private Double amount;
    private BankAccountDTO bankAccount;
    private String transfertType;
}
