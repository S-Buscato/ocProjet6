package com.paymybuddy.paymybuddy.dto;

import com.paymybuddy.paymybuddy.models.Transfert;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TransfertFromBankDTO {
    private Date date;
    private Double amount;
    private String description;
    private String transfertType;
}
