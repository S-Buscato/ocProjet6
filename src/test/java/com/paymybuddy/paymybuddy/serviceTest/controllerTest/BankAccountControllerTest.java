package com.paymybuddy.paymybuddy.serviceTest.controllerTest;

import com.paymybuddy.paymybuddy.dto.BankAccountDTO;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.exception.BankAccountExistsException;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.service.BankAccountService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class BankAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UsersService usersService;

    @MockBean
    UtilsService utilsService;

    @MockBean
    BankAccountService bankAccountService;

    @Test
    @DisplayName("test banckAccount Succes")
    void testBanckAccount() throws Exception {
        when(usersService.findCurrentUserInfo(anyLong())).thenReturn(new UsersDTO());
        when(utilsService.findCurrentUser()).thenReturn(new Users());

        mockMvc.perform(MockMvcRequestBuilders.get("/paymybuddy/bank/account")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        verify(usersService,times(1)).findCurrentUserInfo(anyLong());
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test addBankAccount Succes")
    void testAddBankAccount() throws Exception {
        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(bankAccountService.save(any(BankAccountDTO.class), anyLong())).thenReturn(new BankAccountDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/bank/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        verify(utilsService,times(1)).findCurrentUser();
        verify(bankAccountService,times(1)).save(any(BankAccountDTO.class), anyLong());
    }

    @Test
    @DisplayName("test addBankAccountBankAccountExistsException Succes")
    void testAddBankAccountBankAccountExistsException() throws Exception {
        Exception e = new BankAccountExistsException();

        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(bankAccountService.save(any(BankAccountDTO.class), anyLong())).thenThrow(e);

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/bank/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        verify(utilsService,times(1)).findCurrentUser();
        verify(bankAccountService,times(1)).save(any(BankAccountDTO.class), anyLong());
    }

    @Test
    @DisplayName("test toggleActif Succes")
    void testToggleActif() throws Exception {
        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(usersService.findCurrentUserInfo(anyLong())).thenReturn(new UsersDTO());
        when(bankAccountService.toggleActiveBankAccount(any(BankAccountDTO.class))).thenReturn(new BankAccountDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/bank/toggleActif")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        verify(utilsService,times(1)).findCurrentUser();
        verify(bankAccountService,times(1)).toggleActiveBankAccount(any(BankAccountDTO.class));
        verify(usersService,times(1)).findCurrentUserInfo(anyLong());

    }
}
