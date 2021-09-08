package com.paymybuddy.paymybuddy.IT;

import com.paymybuddy.paymybuddy.constant.TransfertType;
import com.paymybuddy.paymybuddy.dto.BankTransfertDTO;
import com.paymybuddy.paymybuddy.dto.TransfertDTO;
import com.paymybuddy.paymybuddy.exception.BadTransfertTypeException;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.NotActiveBankAccountException;
import com.paymybuddy.paymybuddy.models.BankAccount;
import com.paymybuddy.paymybuddy.models.Transfert;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.BankAccountRepository;
import com.paymybuddy.paymybuddy.repository.TransfertRepository;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.TransfertService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class TransfertIT {

    @Autowired
    TransfertService transfertService;

    @Autowired
    TransfertRepository transfertRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UsersRepository usersRepository;

    Users users = new Users();
    BankAccount bankAccount = new BankAccount();
    List<BankAccount> bankAccountList = new ArrayList();
    BankTransfertDTO bankTransfertDTO = new BankTransfertDTO();
    Transfert transfert = new Transfert();
    List<Transfert> transfertList = new ArrayList();

    @BeforeEach
    public void setUp() {

        users.setFirstName("John");
        users.setLastName("Doe");
        users.setEmail("john@doe.mail");
        users.setTotalAmount(100.0);
        users.setFriends(new ArrayList<>());

        bankAccount.setUsers(users);
        bankAccount.setActif(true);
        bankAccount.setBankName("DoeBank");
        bankAccount.setIban("01234");
        bankAccount.setDescription("leCompteDeJohn");
        //bankAccountList.add(bankAccount);
        //users.setBankAccounts(bankAccountList);

        transfert.setBankAccount(bankAccount);
        transfert.setTransfertType(TransfertType.VIR_FROM_BANK_ACCOUNT.toString());
        transfert.setDate(new Date());
        transfert.setAmount(100.00);
        transfert.setUsers(users);
        transfertList.add(transfert);
    }

    @Test
    @DisplayName("test SaveTransferToBank Succes")
    void testSaveTransferToBank() throws NotActiveBankAccountException, BadTransfertTypeException, InsuffisientBalanceException {
        usersRepository.save(users);
        bankAccountRepository.save(bankAccount);

        bankTransfertDTO.setTransfertType(TransfertType.VIR_TO_BANK_ACCOUNT.toString());
        bankTransfertDTO.setUserEmail(users.getEmail());
        bankTransfertDTO.setAmount(100.00);
        bankTransfertDTO.setIban("01234");

        TransfertDTO transfertDTO = transfertService.sendMoneyToBankAccount(bankTransfertDTO);

        Assertions.assertEquals( 100.00,transfertDTO.getAmount());
        Assertions.assertEquals(bankAccount.getIban(),transfertDTO.getBankAccount().getIban());
    }

    @Test
    @DisplayName("test SaveTransferFromBank Succes")
    void testSaveTransferFromBank() throws NotActiveBankAccountException, BadTransfertTypeException {
        usersRepository.save(users);
        bankAccountRepository.save(bankAccount);

        bankTransfertDTO.setTransfertType(TransfertType.VIR_FROM_BANK_ACCOUNT.toString());
        bankTransfertDTO.setUserEmail(users.getEmail());
        bankTransfertDTO.setAmount(100.00);
        bankTransfertDTO.setIban("01234");

        TransfertDTO transfertDTO = transfertService.receiveMoneyFromBankAccount(bankTransfertDTO);

        Assertions.assertEquals( 100.00,transfertDTO.getAmount());
        Assertions.assertEquals(bankAccount.getIban(),transfertDTO.getBankAccount().getIban());
    }

}
