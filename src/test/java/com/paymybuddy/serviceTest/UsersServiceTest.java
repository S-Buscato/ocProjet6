package com.paymybuddy.serviceTest;

import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = UsersService.class)
public class UsersServiceTest {

    @Autowired
    UsersService usersService;

    @MockBean
    UsersRepository usersRepository;

    Users users = new Users();
    Users users2 = new Users();
    List<Users> usersList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        users.setId(1);
        users.setFirstName("John");
        users.setLastName("Doe");
        users.setEmail("jd@dj.com");
        users.setPassword("1234");
        users.setTotalAmount(100.0);
        users.setFriends(new ArrayList<>());

        usersList.add(users);
        users2.setId(2);
        users2.setFirstName("Jack");
        users2.setLastName("Sparrow");
        users2.setEmail("js@ds.com");
        users2.setPassword("1234");
        users2.setTotalAmount(100.0);
        users2.setFriends(usersList);
    }

    @Test
    @DisplayName("test usersFindByAll Succes")
    void testUsersFindByAll(){
        when(usersRepository.findAll()).thenReturn(usersList);

        Iterable<UsersDTO> usersList = usersService.findall();
        Iterator<UsersDTO> i = usersList.iterator();
        Assertions.assertEquals(users2, i.next());

        verify(usersRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("test usersFindById Succes")
    void testUsersFindById(){
        when(usersRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(users));

        Users usersResult = usersService.findById(1L);

        Assertions.assertEquals(users, usersResult );

        verify(usersRepository, times(1)).findById(1L);

    }

    @Test
    @DisplayName("test usersAddUserFriends Succes")
    void testUsersAddUserFriends(){

        List<UsersMinimalsInfoDTO> usersList = new ArrayList<>();

//        UsersFriendsDTO usersFriendsDTO = new UsersFriendsDTO();
//        usersFriendsDTO.setFirstName(users2.getFirstName());
//        usersFriendsDTO.setLastName(users2.getLastName());
//        usersFriendsDTO.setEmail(users2.getEmail());
//        usersList.add(usersFriendsDTO);
//
//        UsersDTO usersDTO2 = new UsersDTO();
//        usersDTO2.setFirstName(users.getFirstName());
//        usersDTO2.setLastName(users.getLastName());
//        usersDTO2.setTotalAmount(users.getTotalAmount());
//        usersDTO2.setEmail(users.getEmail());
//        usersDTO2.setFriends(usersList);

/*
        when(usersRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(users));
        when(usersRepository.findById(2L)).thenReturn(java.util.Optional.ofNullable(users2));

        Users usersResult = usersService.addFriends(1L,2L);

        Assertions.assertTrue(usersResult.getFriends().equals(users2));

        verify(usersRepository, times(1)).findById(1L);
        verify(usersRepository, times(1)).findById(2L);*/

    }

}

