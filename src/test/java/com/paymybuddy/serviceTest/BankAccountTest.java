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
        users.setBankAccounts(new ArrayList<>());


        bankAccount.setId(1);
        bankAccount.setUsers(users);
        bankAccount.setActif(true);
        bankAccount.setBankName("DoeBank");
        bankAccount.setIban("01234");
        bankAccount.setDescription("leCompteDeJohn");
        users.getBankAccounts().add(bankAccount);

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

    @Test
    @DisplayName("test getAllBankAccount Succes")
    void testFindAllBankAccount(){

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setId(2);
        bankAccount2.setUsers(users);
        bankAccount2.setActif(true);
        bankAccount2.setBankName("DoeBank2");
        bankAccount2.setIban("56789");
        bankAccount2.setDescription("leCompteDeJohn2");
        users.getBankAccounts().add(bankAccount2);

        //TODO : peut etre passer direct par le repo de bankAccount ?
        when(bankAccountRepository.findAll()).thenReturn(bankAccountList);
        when(usersRepository.findById(anyLong())).thenReturn(Optional.ofNullable(users));

        List<BankAccountDTO> bankAccountResponse = bankAccountService.findAll(1L);

        Assertions.assertEquals(bankAccount2.getBankName(), bankAccountResponse.get(1).getBankName());
        Assertions.assertTrue(bankAccountResponse.size() == 2);

        verify(usersRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("test getBankAccountById Succes")
    void testFindBankAccountById(){

        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.ofNullable(bankAccount));

        BankAccountDTO bankAccountResponse = bankAccountService.findById(1L);

        Assertions.assertEquals(bankAccount.getIban(), bankAccountResponse.getIban());

        verify(bankAccountRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("test getBankAccountByIban Succes")
    void testFindBankAccountByIban(){

        when(bankAccountRepository.findByIban(anyString())).thenReturn(bankAccount);

        BankAccountDTO bankAccountResponse = bankAccountService.findByIban("01234");

        Assertions.assertEquals(bankAccount.getIban(), bankAccountResponse.getIban());

        verify(bankAccountRepository, times(1)).findByIban(anyString());
    }

    @Test
    @DisplayName("test updateBankAccount Succes")
    void testUpdateBankAccount(){

        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.ofNullable(bankAccount));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        BankAccountDTO bankAccount2 = new BankAccountDTO();
        bankAccount2.setActif(true);
        bankAccount2.setBankName("DoeBank2");
        bankAccount2.setIban("56789");
        bankAccount2.setDescription("leCompteDeJohn2");

        BankAccountDTO bankAccountResponse = bankAccountService.update(1L, bankAccount2);

        Assertions.assertEquals(bankAccount2.getIban(), bankAccountResponse.getIban());

        verify(bankAccountRepository, times(1)).findById(anyLong());
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));

    }

    @Test
    @DisplayName("test deleteBankAccount Succes")
    void testDeleteBankAccount(){

        bankAccountService.deleteById(1L);

        verify(bankAccountRepository, times(1)).deleteById(anyLong());

    }
}
