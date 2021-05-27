package com.paymybuddy.paymybuddy.dto;

import com.paymybuddy.paymybuddy.models.Transfert;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BankTransfertDTO {
    private Date date;
    private Double amount;
    private String transfertType;
    private String iban;
    private String userEmail;
}
