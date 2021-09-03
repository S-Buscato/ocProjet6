package com.paymybuddy.paymybuddy.serviceTest.controllerTest;

import com.paymybuddy.paymybuddy.dto.EmmetedTransactionDTO;
import com.paymybuddy.paymybuddy.dto.RequestTransactionDTO;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.UsersNotInFriendsListException;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.service.TransactionService;
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
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UsersService usersService;

    @MockBean
    UtilsService utilsService;

    @MockBean
    TransactionService transactionService;

    @Test
    @DisplayName("test toSendMoney Succes")
    void testToSendMoney() throws Exception {
        when(usersService.findCurrentUserInfo(anyLong())).thenReturn(new UsersDTO());
        when(utilsService.findCurrentUser()).thenReturn(new Users());
        when(usersService.findByEmail(anyString())).thenReturn(java.util.Optional.of(new Users()));

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/users/transaction")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("receiverEmail","test"))
                .andExpect(status().isOk());

        verify(usersService,times(1)).findCurrentUserInfo(anyLong());
        verify(usersService,times(1)).findByEmail(anyString());
        verify(utilsService,times(1)).findCurrentUser();
    }

    @Test
    @DisplayName("test SendMoney Succes")
    void testSendMoney() throws Exception {
        Users users = new Users();
        users.setEmail("john");
        UsersMinimalsInfoDTO usersMinimalsInfoDTO = new UsersMinimalsInfoDTO();
        usersMinimalsInfoDTO.setLastName("John");
        usersMinimalsInfoDTO.setEmail("johnMail");
        usersMinimalsInfoDTO.setFirstName("Doe");

        RequestTransactionDTO requestTransactionDTO =  new RequestTransactionDTO();
        requestTransactionDTO.setAmount(50);
        requestTransactionDTO.setReceiver(usersMinimalsInfoDTO);
        requestTransactionDTO.setDescription("test");

        when(usersService.findByEmail(anyString())).thenReturn(java.util.Optional.of(users));
        when(usersService.findCurrentUserInfo(anyLong())).thenReturn(new UsersDTO());
        when(utilsService.findCurrentUser()).thenReturn(users);
        when(transactionService.sendMoneyToFriends(anyLong(), any(RequestTransactionDTO.class))).thenReturn(new EmmetedTransactionDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/users/sendMoney")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("requestTransactionDTO",requestTransactionDTO))
                .andExpect(status().isOk());

        verify(usersService,times(3)).findCurrentUserInfo(anyLong());
        verify(usersService,times(1)).findByEmail(anyString());
        verify(utilsService,times(1)).findCurrentUser();
        verify(usersService,times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("test sendMoneyInsuffisientBalanceException Succes")
    void testSendMoneyInsuffisientBalanceException() throws Exception {
        Exception e = new InsuffisientBalanceException();

        Users users = new Users();
        users.setEmail("john");
        UsersMinimalsInfoDTO usersMinimalsInfoDTO = new UsersMinimalsInfoDTO();
        usersMinimalsInfoDTO.setLastName("John");
        usersMinimalsInfoDTO.setEmail("johnMail");
        usersMinimalsInfoDTO.setFirstName("Doe");

        RequestTransactionDTO requestTransactionDTO =  new RequestTransactionDTO();
        requestTransactionDTO.setAmount(50);
        requestTransactionDTO.setReceiver(usersMinimalsInfoDTO);
        requestTransactionDTO.setDescription("test");

        when(usersService.findByEmail(anyString())).thenReturn(java.util.Optional.of(users));
        when(usersService.findCurrentUserInfo(anyLong())).thenReturn(new UsersDTO());
        when(utilsService.findCurrentUser()).thenReturn(users);
        when(transactionService.sendMoneyToFriends(anyLong(), any(RequestTransactionDTO.class))).thenThrow(e);

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/users/sendMoney")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("requestTransactionDTO",requestTransactionDTO))
                .andExpect(status().isOk());

        verify(usersService,times(2)).findCurrentUserInfo(anyLong());
        verify(usersService,times(1)).findByEmail(anyString());
        verify(utilsService,times(1)).findCurrentUser();
        verify(usersService,times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("test sendMoneyUsersNotInFriendsListException Succes")
    void testSendMoneyUsersNotInFriendsListException() throws Exception {
        Exception e = new UsersNotInFriendsListException();

        Users users = new Users();
        users.setEmail("john");
        UsersMinimalsInfoDTO usersMinimalsInfoDTO = new UsersMinimalsInfoDTO();
        usersMinimalsInfoDTO.setLastName("John");
        usersMinimalsInfoDTO.setEmail("johnMail");
        usersMinimalsInfoDTO.setFirstName("Doe");

        RequestTransactionDTO requestTransactionDTO =  new RequestTransactionDTO();
        requestTransactionDTO.setAmount(50);
        requestTransactionDTO.setReceiver(usersMinimalsInfoDTO);
        requestTransactionDTO.setDescription("test");

        when(usersService.findByEmail(anyString())).thenReturn(java.util.Optional.of(users));
        when(usersService.findCurrentUserInfo(anyLong())).thenReturn(new UsersDTO());
        when(utilsService.findCurrentUser()).thenReturn(users);
        when(transactionService.sendMoneyToFriends(anyLong(), any(RequestTransactionDTO.class))).thenThrow(e);

        mockMvc.perform(MockMvcRequestBuilders.post("/paymybuddy/users/sendMoney")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("requestTransactionDTO",requestTransactionDTO))
                .andExpect(status().isOk());

        verify(usersService,times(2)).findCurrentUserInfo(anyLong());
        verify(usersService,times(1)).findByEmail(anyString());
        verify(utilsService,times(1)).findCurrentUser();
        verify(usersService,times(1)).findByEmail(anyString());
    }

}
