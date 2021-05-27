package com.paymybuddy.paymybuddy.dto.mapper;

import com.paymybuddy.paymybuddy.dto.UserSubscribeOkDTO;
import com.paymybuddy.paymybuddy.dto.UsersSubscribeDTO;
import com.paymybuddy.paymybuddy.models.Users;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Mapper(componentModel="spring")
@Service
public interface UsersSubscribeOkMapper {

    UsersSubscribeOkMapper INSTANCE = Mappers.getMapper(UsersSubscribeOkMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserSubscribeOkDTO convertUsersToUserSubscribeOkDTO(Users entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Users convertUsersSubscribeDTOToUsers(UserSubscribeOkDTO usersSubscribeDTO);

}
