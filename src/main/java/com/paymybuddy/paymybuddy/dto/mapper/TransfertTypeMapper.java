package com.paymybuddy.paymybuddy.dto.mapper;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

@Mapper(componentModel="spring")
@Service
abstract class TransfertTypeMapper {

    /*@BeforeMapping
    protected void validate(Transfert transfert) {
        if(transfert.getTransfertType().equals(TransfertType.VIR_FROM_BANK_ACCOUNT)){
            convertTransfertFromBank(transfert);
        }else{
            convertTransfertToBank(transfert);
        }
    }



    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public  TransfertFromBankDTO convertTransfertFromBank(Transfert transfert){
        TransfertFromBankDTO transfertFromBankDTO = new TransfertFromBankDTO();
        if(transfert.getTransfertType().equals(TransfertType.VIR_TO_BANK_ACCOUNT)){
            transfertFromBankDTO.setAmount(transfert.getAmount());
            transfertFromBankDTO.setDate(transfert.getDate());
            transfertFromBankDTO.setDescription(transfert.getTransfertType());
        }
        return transfertFromBankDTO;
    }

    @AfterMapping
    public abstract List<TransfertFromBankDTO> convertTransfertFromBank(
            Collection<TransfertFromBankDTO> transfertFromBankDTOS);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public TransfertToBankDTO convertTransfertToBank(Transfert transfert){
        TransfertToBankDTO transfertToBankDTO = new TransfertToBankDTO();
        if(transfert.getTransfertType().equals(TransfertType.VIR_TO_BANK_ACCOUNT)){
            transfertToBankDTO.setAmount(transfert.getAmount());
            transfertToBankDTO.setDate(transfert.getDate());
            transfertToBankDTO.setDescription(transfert.getTransfertType());
        }
        return transfertToBankDTO;
    }*/
/*
    public abstract List<TransfertToBankDTO> convertTransfertFromBank(
            Collection<TransfertToBankDTO> transfertToBankDTOS);*/
}
