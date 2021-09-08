package com.paymybuddy.paymybuddy.controllerTest;

import com.paymybuddy.paymybuddy.dto.BankTransfertDTO;
import com.paymybuddy.paymybuddy.dto.TransfertDTO;
import com.paymybuddy.paymybuddy.exception.BadTransfertTypeException;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.NotActiveBankAccountException;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.service.TransfertService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class TransfertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UsersService usersService;

    @MockBean
    UtilsService utilsService;

    @MockBean
    TransfertService transfertService;

    @Test
    @DisplayName("test receiveMoneyFromBankAccount Succes")
    void testReceiveMoneyFromBankAccount() throws Exception {
        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(transfertService.receiveMoneyFromBankAccount(any(BankTransfertDTO.class))).thenReturn(new TransfertDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy//transfertFromBank")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        verify(transfertService,times(1)).receiveMoneyFromBankAccount(any(BankTransfertDTO.class));
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test receiveMoneyFromBankAccountNotActiveBankAccountException Succes")
    void testReceiveMoneyFromBankAccountNotActiveBankAccountException() throws Exception {
        Exception e = new NotActiveBankAccountException();

        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(transfertService.receiveMoneyFromBankAccount(any(BankTransfertDTO.class))).thenThrow(e);

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy//transfertFromBank")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        verify(transfertService,times(1)).receiveMoneyFromBankAccount(any(BankTransfertDTO.class));
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test receiveMoneyFromBankAccountBadTransfertTypeException Succes")
    void testReceiveMoneyFromBankAccountBadTransfertTypeException() throws Exception {
        Exception e = new BadTransfertTypeException();

        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(transfertService.receiveMoneyFromBankAccount(any(BankTransfertDTO.class))).thenThrow(e);

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy//transfertFromBank")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        verify(transfertService,times(1)).receiveMoneyFromBankAccount(any(BankTransfertDTO.class));
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test sendMoneyToBankAccount Succes")
    void testSendMoneyToBankAccount() throws Exception {
        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(transfertService.sendMoneyToBankAccount(any(BankTransfertDTO.class))).thenReturn(new TransfertDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/transfertToBank")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        verify(transfertService,times(1)).sendMoneyToBankAccount(any(BankTransfertDTO.class));
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test sendMoneyToBankAccountNotActiveBankAccountException Succes")
    void testSendMoneyToBankAccountNotActiveBankAccountException() throws Exception {
        Exception e = new NotActiveBankAccountException();

        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(transfertService.sendMoneyToBankAccount(any(BankTransfertDTO.class))).thenThrow(e);

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/transfertToBank")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        verify(transfertService,times(1)).sendMoneyToBankAccount(any(BankTransfertDTO.class));
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test sendMoneyToBankAccountBadTransfertTypeException Succes")
    void testSendMoneyToBankAccountBadTransfertTypeException() throws Exception {
        Exception e = new BadTransfertTypeException();

        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(transfertService.sendMoneyToBankAccount(any(BankTransfertDTO.class))).thenThrow(e);

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/transfertToBank")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        verify(transfertService,times(1)).sendMoneyToBankAccount(any(BankTransfertDTO.class));
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test sendMoneyToBankAccountInsuffisientBalanceException Succes")
    void testSendMoneyToBankAccountInsuffisientBalanceException() throws Exception {
        Exception e = new InsuffisientBalanceException();

        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(transfertService.sendMoneyToBankAccount(any(BankTransfertDTO.class))).thenThrow(e);

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/transfertToBank")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        verify(transfertService,times(1)).sendMoneyToBankAccount(any(BankTransfertDTO.class));
        verify(utilsService,times(1)).findCurrentUser();
    }


}
