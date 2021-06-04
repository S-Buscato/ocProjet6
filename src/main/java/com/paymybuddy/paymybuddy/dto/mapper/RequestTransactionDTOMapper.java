package com.paymybuddy.paymybuddy.dto.mapper;

import com.paymybuddy.paymybuddy.dto.ReceivedTransactionDTO;
import com.paymybuddy.paymybuddy.dto.RequestTransactionDTO;
import com.paymybuddy.paymybuddy.models.Transaction;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Mapper(componentModel="spring")
@Service
public interface RequestTransactionDTOMapper {
    RequestTransactionDTOMapper INSTANCE = Mappers.getMapper(RequestTransactionDTOMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RequestTransactionDTO convertTransactionToTransactionDTO(Transaction entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Transaction convertTransactionDTOToTransaction(RequestTransactionDTO requestTransactionDTO);

}
