package com.paymybuddy.paymybuddy.dto.Mapper;

import com.paymybuddy.paymybuddy.dto.TransfertFromBankDTO;
import com.paymybuddy.paymybuddy.models.Transfert;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface TypeEnumMapper{

    TypeEnumMapper INSTANCE = Mappers.getMapper(TypeEnumMapper.class);

        @ValueMappings({
                @ValueMapping(source = "CARD_VISA", target = "CARD"),
                @ValueMapping(source = "CARD_MASTER", target = "CARD"),
                @ValueMapping(source = "CARD_CREDIT", target = "CARD")
        })
        TransfertFromBankDTO paymentTypeToPaymentTypeView(Transfert transfert);
}

