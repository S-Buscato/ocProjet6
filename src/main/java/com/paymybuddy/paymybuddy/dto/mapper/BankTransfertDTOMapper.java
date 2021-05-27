package com.paymybuddy.paymybuddy.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Mapper(componentModel="spring")
@Service
public interface BankTransfertDTOMapper {
    BankTransfertDTOMapper INSTANCE = Mappers.getMapper(BankTransfertDTOMapper.class);
}
