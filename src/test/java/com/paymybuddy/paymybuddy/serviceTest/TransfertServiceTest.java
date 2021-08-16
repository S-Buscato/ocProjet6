package com.paymybuddy.paymybuddy.serviceTest;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = TransfertService.class)
public class TransfertServiceTest {

    @Autowired
    TransfertService transfertService;

    @MockBean
    TransfertRepository transfertRepository;

    @MockBean
    UsersRepository usersRepository;

    @MockBean
    BankAccountRepository bankAccountRepository;

    Users users = new Users();
    BankAccount bankAccount = new BankAccount();
    List<BankAccount> bankAccountList = new ArrayList();
    BankTransfertDTO bankTransfertDTO = new BankTransfertDTO();
    Transfert transfert = new Transfert();
    List<Transfert> transfertList = new ArrayList();


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

        transfert.setBankAccount(bankAccount);
        transfert.setTransfertType(TransfertType.VIR_FROM_BANK_ACCOUNT.toString());
        transfert.setDate(new Date());
        transfert.setAmount(100.00);
        transfert.setUsers(users);
        transfert.setId(1);

        transfertList.add(transfert);
       }

    @Test
    @DisplayName("test receiveMoneyFromBankAccount Succes")
    void testReceiveMoneyFromBankAccount() throws BadTransfertTypeException, NotActiveBankAccountException {

        bankTransfertDTO.setTransfertType(TransfertType.VIR_FROM_BANK_ACCOUNT.toString());
        bankTransfertDTO.setAmount(100.00);
        bankTransfertDTO.setIban(bankAccount.getIban());
        bankTransfertDTO.setUserEmail(users.getEmail());

        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(users));
        when(usersRepository.save(any(Users.class))).thenReturn(users);
        when(bankAccountRepository.findByIban(anyString())).thenReturn(bankAccount);
        when(transfertRepository.save(any(Transfert.class))).thenReturn(transfert);

        TransfertDTO transfertDTO = transfertService.receiveMoneyFromBankAccount(bankTransfertDTO);

        Assertions.assertEquals(200.00, users.getTotalAmount());
        Assertions.assertEquals(TransfertType.VIR_FROM_BANK_ACCOUNT.toString(), transfertDTO.getTransfertType());

        Assertions.assertTrue(transfertDTO.getDate() != null);

        verify(usersRepository, times(1)).findByEmail(anyString());
        verify(usersRepository, times(1)).save(any(Users.class));
        verify(bankAccountRepository, times(1)).findByIban(anyString());
        verify(transfertRepository, times(1)).save(any(Transfert.class));
    }

    @Test
    @DisplayName("test sendMoneyToBankAccount Succes")
    void testSendMoneyToBankAccount() throws BadTransfertTypeException, NotActiveBankAccountException, InsuffisientBalanceException {

        bankTransfertDTO.setTransfertType(TransfertType.VIR_TO_BANK_ACCOUNT.toString());
        bankTransfertDTO.setAmount(100.00);
        bankTransfertDTO.setIban(bankAccount.getIban());
        bankTransfertDTO.setUserEmail(users.getEmail());

        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(users));
        when(usersRepository.save(any(Users.class))).thenReturn(users);
        when(bankAccountRepository.findByIban(anyString())).thenReturn(bankAccount);
        when(transfertRepository.save(any(Transfert.class))).thenReturn(transfert);

        TransfertDTO transfertDTO = transfertService.sendMoneyToBankAccount(bankTransfertDTO);

        Assertions.assertEquals(0, users.getTotalAmount());
        Assertions.assertEquals(TransfertType.VIR_TO_BANK_ACCOUNT.toString(), transfertDTO.getTransfertType());

        Assertions.assertTrue(transfertDTO.getDate() != null);

        verify(usersRepository, times(1)).findByEmail(anyString());
        verify(usersRepository, times(1)).save(any(Users.class));
        verify(bankAccountRepository, times(1)).findByIban(anyString());
        verify(transfertRepository, times(1)).save(any(Transfert.class));
    }

    @Test
    @DisplayName("test findAllTransfert Succes")
    void testFindAllTransfert()  {

        when(transfertRepository.findAllByUsersId(anyLong())).thenReturn(transfertList);

        List<Transfert> transferts = transfertService.findAllByUsersId(1L);

        Assertions.assertEquals(1, transferts.size());
        Assertions.assertEquals(100.00, transferts.get(0).getAmount());

        verify(transfertRepository, times(1)).findAllByUsersId(anyLong());
    }

    @Test
    @DisplayName("test transfertFindById Succes")
    void testTransfertFindById()  {

        when(transfertRepository.findById(anyLong())).thenReturn(Optional.ofNullable(transfert));

        TransfertDTO transfertDTO = transfertService.findById(1L);

        Assertions.assertEquals(100.00, transfertDTO.getAmount());

        verify(transfertRepository, times(1)).findById(anyLong());
    }


}
