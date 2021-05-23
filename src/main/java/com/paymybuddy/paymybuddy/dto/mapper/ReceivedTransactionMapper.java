package com.paymybuddy.paymybuddy.dto.Mapper;


import com.paymybuddy.paymybuddy.dto.ReceivedTransactionDTO;
import com.paymybuddy.paymybuddy.models.Transaction;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Mapper(componentModel="spring")
@Service
public interface ReceivedTransactionMapper {
    ReceivedTransactionMapper INSTANCE = Mappers.getMapper(ReceivedTransactionMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ReceivedTransactionDTO convertTransactionToTransactionDTO(Transaction entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Transaction convertTransactionDTOToTransaction(ReceivedTransactionDTO receivedTransactionDTO);
}
