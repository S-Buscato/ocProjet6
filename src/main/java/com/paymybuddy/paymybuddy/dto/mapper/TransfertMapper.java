package com.paymybuddy.paymybuddy.dto.mapper;

import com.paymybuddy.paymybuddy.constant.TransfertType;
import com.paymybuddy.paymybuddy.dto.TransfertDTO;
import com.paymybuddy.paymybuddy.models.Transfert;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Mapper(componentModel="spring", imports = {TransfertType.class})
@Service
public interface TransfertMapper {
    TransfertMapper INSTANCE = Mappers.getMapper(TransfertMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TransfertDTO convertTransfertToTransfertDTO(Transfert entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Transfert convertTransfertDTOToTransfert(TransfertDTO TransfertDTO);


}
