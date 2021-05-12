package com.paymybuddy.paymybuddy.DTO.Mapper;

import com.paymybuddy.paymybuddy.DTO.UsersDTO;
import com.paymybuddy.paymybuddy.DTO.UsersFriendsDTO;
import com.paymybuddy.paymybuddy.Models.Users;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel="spring")
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UsersDTO convertUsersToUsersDTO(Users entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Users convertUsersDTOToUsers(UsersDTO usersDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UsersFriendsDTO convertUsersToUsersFriendsDTO(Users entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Users convertUserFriendsDTOToUsers(UsersFriendsDTO usersFriendsDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<UsersDTO> convertUsersToUsersDTOList(List<Users> usersList);


}
