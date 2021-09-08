package com.paymybuddy.paymybuddy.controllerTest;

import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.exception.UserAllReadyExistException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.service.UsersService;
import com.paymybuddy.paymybuddy.service.UtilsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UsersService usersService;

    @MockBean
    UtilsService utilsService;

    @Test
    @DisplayName("test findAllUsers Succes")
    void testfindAllUsers() throws Exception {
        when(usersService.findall()).thenReturn(new ArrayList<>());
        when(utilsService.findCurrentUser()).thenReturn(new Users());

        mockMvc.perform(get("/paymybuddy/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(usersService,times(1)).findall();
        verify(utilsService,times(1)).findCurrentUser();
    }

   @Test
    @DisplayName("test findUsersInfo Succes")
    void testFindUsersInfo() throws Exception {
        when(usersService.findCurrentUserInfo(anyLong())).thenReturn(new UsersDTO());
        when(utilsService.findCurrentUser()).thenReturn(new Users());

        mockMvc.perform(get("/paymybuddy/history")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(usersService,times(1)).findCurrentUserInfo(anyLong());
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test getUsersInfo Succes")
    void testGetUsersInfo() throws Exception {
        when(usersService.findCurrentUserInfo(anyLong())).thenReturn(new UsersDTO());
        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(usersService.findUsersFriends(anyLong())).thenReturn(new UsersDTO());

        mockMvc.perform(get("/paymybuddy/search")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(usersService,times(1)).findCurrentUserInfo(anyLong());
        verify(usersService,times(1)).findUsersFriends(anyLong());
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test getUsersInfoUsersNotFoundException Succes")
    void testGetUsersInfoUsersNotFoundException() throws Exception {
        Exception e = new UsersNotFoundException();

        when(usersService.findCurrentUserInfo(anyLong())).thenReturn(new UsersDTO());
        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(usersService.findUsersFriends(anyLong())).thenThrow(e);

        mockMvc.perform(get("/paymybuddy/search")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(usersService,times(1)).findCurrentUserInfo(anyLong());
        verify(usersService,times(1)).findUsersFriends(anyLong());
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test myProfil Succes")
    void testMyProfil() throws Exception {
        when(usersService.findCurrentUserInfo(anyLong())).thenReturn(new UsersDTO());
        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(usersService.findUsersFriends(anyLong())).thenReturn(new UsersDTO());

        mockMvc.perform(get("/paymybuddy/myprofil")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(usersService,times(1)).findCurrentUserInfo(anyLong());
        verify(usersService,times(1)).findUsersFriends(anyLong());
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test addFriends Succes")
    void testAddFriends() throws Exception {
        when(usersService.addFriends(anyLong(),anyString())).thenReturn(new UsersMinimalsInfoDTO());
        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(usersService.findUsersFriends(anyLong())).thenReturn(new UsersDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/users/addfriends")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("email","test"))
                .andExpect(status().isOk());

        verify(usersService,times(1)).addFriends(anyLong(), anyString());
        verify(usersService,times(1)).findUsersFriends(anyLong());
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test addFriendsUsersNotFoundException Succes")
    void testAddFriendsUsersNotFoundException() throws Exception {

        Exception e = new UsersNotFoundException();

        when(usersService.addFriends(anyLong(),anyString())).thenThrow(e);
        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(usersService.findUsersFriends(anyLong())).thenReturn(new UsersDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/users/addfriends")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("email","test"))
                .andExpect(status().isOk());

        verify(usersService,times(1)).addFriends(anyLong(), anyString());
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test addFriendsUserAllReadyExistException Succes")
    void testAddFriendsUserAllReadyExistException() throws Exception {

        Exception e = new UserAllReadyExistException();

        when(usersService.addFriends(anyLong(),anyString())).thenThrow(e);
        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(usersService.findUsersFriends(anyLong())).thenReturn(new UsersDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/users/addfriends")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("email","test"))
                .andExpect(status().isOk());

        verify(usersService,times(1)).addFriends(anyLong(), anyString());
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test RemoveFriends Succes")
    void testRemoveFriends() throws Exception {
        when(usersService.removeFriends(anyLong(),anyString())).thenReturn(new UsersDTO());
        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(usersService.findUsersFriends(anyLong())).thenReturn(new UsersDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/users/removefriends")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("removeUserEmail","test"))
                .andExpect(status().isOk());

        verify(usersService,times(1)).removeFriends(anyLong(), anyString());
        verify(usersService,times(1)).findUsersFriends(anyLong());
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test RemoveFriendsUsersNotFoundException Succes")
    void testRemoveFriendsUsersNotFoundException() throws Exception {

        Exception e = new UsersNotFoundException();

        when(usersService.removeFriends(anyLong(),anyString())).thenThrow(e);
        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(usersService.findUsersFriends(anyLong())).thenReturn(new UsersDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/users/removefriends")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("removeUserEmail","test"))
                .andExpect(status().isOk());

        verify(usersService,times(1)).removeFriends(anyLong(), anyString());
        verify(utilsService,times(1)).findCurrentUser();
    }


}
