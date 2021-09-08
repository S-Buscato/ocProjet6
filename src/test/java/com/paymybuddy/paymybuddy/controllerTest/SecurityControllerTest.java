package com.paymybuddy.paymybuddy.controllerTest;

import com.paymybuddy.paymybuddy.dto.UserSubscribeOkDTO;
import com.paymybuddy.paymybuddy.dto.UsersSubscribeDTO;
import com.paymybuddy.paymybuddy.exception.ExistingEmailException;
import com.paymybuddy.paymybuddy.service.UsersService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class SecurityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UsersService usersService;

    @Test
    @DisplayName("test home Succes")
    void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test Login Succes")
    void testLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test register Succes")
    void testRegister() throws Exception {
        when(usersService.subscribe(any(UsersSubscribeDTO.class))).thenReturn(new UserSubscribeOkDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        verify(usersService,times(1)).subscribe(any(UsersSubscribeDTO.class));
    }

    @Test
    @DisplayName("test registerExistingEmailException Succes")
    void testRegisterExistingEmailException() throws Exception {
        Exception e = new ExistingEmailException();

        when(usersService.subscribe(any(UsersSubscribeDTO.class))).thenThrow(e);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        verify(usersService,times(1)).subscribe(any(UsersSubscribeDTO.class));
    }
}
