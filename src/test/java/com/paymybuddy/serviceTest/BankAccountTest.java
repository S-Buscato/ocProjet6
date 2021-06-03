package com.paymybuddy.serviceTest;

import com.paymybuddy.paymybuddy.dto.BankAccountDTO;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.exception.BankAccountExistsException;
import com.paymybuddy.paymybuddy.models.BankAccount;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.BankAccountRepository;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.BankAccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = BankAccountService.class)
public class BankAccountTest {

    @Autowired
    BankAccountService bankAccountService;

    @MockBean
    BankAccountRepository bankAccountRepository;

    @MockBean
    UsersRepository usersRepository;

    BankAccount bankAccount = new BankAccount();
    BankAccountDTO bankAccountDTO = new BankAccountDTO();

    List<BankAccount> bankAccountList = new ArrayList();
    Users users = new Users();


    @BeforeEach
    public void setUp() {

        users.setId(1);
        users.setFirstName("John");
        users.setLastName("Doe");
        users.setEmail("john@doe.mail");
        users.setTotalAmount(100.0);
        users.setFriends(new ArrayList<>());

        bankAccount.setId(1);
        bankAccount.setUsers(users);
        bankAccount.setActif(true);
        bankAccount.setBankName("DoeBank");
        bankAccount.setIban("01234");
        bankAccount.setDescription("leCompteDeJohn");
        bankAccountList.add(bankAccount);
        users.setBankAccounts(bankAccountList);

        bankAccountDTO.setBankName(bankAccount.getBankName());
        bankAccountDTO.setIban(bankAccount.getIban());
        bankAccountDTO.setActif(bankAccount.isActif());
        bankAccountDTO.setDescription(bankAccount.getDescription());
    }

    @Test
    @DisplayName("test saveBankAccount Succes")
    void testSaveBankAccount() throws BankAccountExistsException {

        when(bankAccountRepository.findByIban(anyString())).thenReturn(null);
        when(usersRepository.findById(anyLong())).thenReturn(Optional.ofNullable(users));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        BankAccountDTO bankAccountResponse = bankAccountService.save(bankAccountDTO,1L);

        Assertions.assertEquals(bankAccountResponse.getBankName(), bankAccountDTO.getBankName());

        verify(bankAccountRepository, times(1)).findByIban(anyString());
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
        verify(usersRepository, times(1)).findById(anyLong());

    }

    @Test
    @DisplayName("test toggleActiveBankAccount Succes")
    void testToggleActiveBankAccount(){

        when(bankAccountRepository.findByIban(anyString())).thenReturn(bankAccount);
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        BankAccountDTO bankAccountResponse = bankAccountService.toggleActiveBankAccount(bankAccountDTO);

        Assertions.assertFalse(bankAccountResponse.isActif());

        verify(bankAccountRepository, times(1)).findByIban(anyString());
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
    }
}
