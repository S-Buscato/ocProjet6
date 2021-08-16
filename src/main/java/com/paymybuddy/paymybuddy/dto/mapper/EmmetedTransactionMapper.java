package com.paymybuddy.paymybuddy.dto.mapper;


import com.paymybuddy.paymybuddy.dto.EmmetedTransactionDTO;
import com.paymybuddy.paymybuddy.models.Transaction;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel="spring")
@Service
public interface EmmetedTransactionMapper {
    EmmetedTransactionMapper INSTANCE = Mappers.getMapper(EmmetedTransactionMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EmmetedTransactionDTO convertTransactionToTransactionDTO(Transaction entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<EmmetedTransactionDTO> convertTransactionListToTransactionDTOList(List<Transaction> entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Transaction convertTransactionDTOToTransaction(EmmetedTransactionDTO emmetedTransactionDTO);
}
