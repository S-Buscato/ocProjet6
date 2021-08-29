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
public class BankTransfertDTO {
    private Date date;
    private Double amount;
    private String transfertType;
    private String iban;
    private String userEmail;
}
