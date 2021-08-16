package com.paymybuddy.paymybuddy.serviceTest.IT;

import com.paymybuddy.paymybuddy.dto.BankAccountDTO;
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
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class BankAccountIT {

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UsersRepository usersRepository;

    BankAccount bankAccount = new BankAccount();
    BankAccountDTO bankAccountDTO = new BankAccountDTO();

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
        //users.getBankAccounts().add(bankAccount);

        bankAccountDTO.setBankName(bankAccount.getBankName());
        bankAccountDTO.setIban(bankAccount.getIban());
        bankAccountDTO.setActif(bankAccount.isActif());
        bankAccountDTO.setDescription(bankAccount.getDescription());
    }

    @Test
    @DisplayName("test saveBankAccount Succes")
    void testSaveBankAccount() throws BankAccountExistsException {
        usersRepository.save(users);
        BankAccountDTO bankAccountResponse = bankAccountService.save(bankAccountDTO,1L);

        Users users = usersRepository.findById(1L).get();

        Assertions.assertEquals(bankAccountDTO.getIban(), bankAccountResponse.getIban());
        Assertions.assertEquals(bankAccountDTO.getIban(), users.getBankAccounts().get(0).getIban());
    }

    @Test
    @DisplayName("test toggleActiveBankAccount Succes")
    void testToggleActiveBankAccount() throws BankAccountExistsException {

        usersRepository.save(users);
        BankAccountDTO bankAccountResponse = bankAccountService.save(bankAccountDTO,1L);
        BankAccountDTO bankAccountResponse1 = bankAccountService.toggleActiveBankAccount(bankAccountDTO);

        Assertions.assertTrue(bankAccountResponse.isActif());
        Assertions.assertFalse(bankAccountResponse1.isActif());
    }

    @Test
    @DisplayName("test getAllBankAccount Succes")
    void testFindAllBankAccount() throws BankAccountExistsException {

        usersRepository.save(users);
        bankAccountService.save(bankAccountDTO,1L);

        List<BankAccountDTO> bankAccountResponse = bankAccountService.findAll(1L);

        Assertions.assertEquals(1, bankAccountResponse.size());
    }

    @Test
    @DisplayName("test updateBankAccount Succes")
    void testUpdateBankAccount() throws BankAccountExistsException {

        usersRepository.save(users);
        bankAccountService.save(bankAccountDTO,1L);

        BankAccountDTO bankAccount2 = new BankAccountDTO();
        bankAccount2.setActif(true);
        bankAccount2.setBankName("DoeBank2");
        bankAccount2.setIban("56789");
        bankAccount2.setDescription("leCompteDeJohn2");

        BankAccountDTO bankAccountResponse = bankAccountService.update(1L, bankAccount2);

        Assertions.assertEquals(bankAccount2.getIban(), bankAccountResponse.getIban());
    }

    @Test
    @DisplayName("test deleteBankAccount Succes")
    void testDeleteBankAccount() throws BankAccountExistsException {

        usersRepository.save(users);
        bankAccountService.save(bankAccountDTO,1L);
        bankAccountService.deleteById(1L);

        Assertions.assertEquals(null,bankAccountService.findByIban("01234"));
    }

}

