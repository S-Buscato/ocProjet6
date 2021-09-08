package com.paymybuddy.paymybuddy.serviceTest.IT;

import com.paymybuddy.paymybuddy.constant.TransfertType;
import com.paymybuddy.paymybuddy.dto.*;
import com.paymybuddy.paymybuddy.exception.ExistingEmailException;
import com.paymybuddy.paymybuddy.exception.UserAllReadyExistException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.exception.UsersNotInFriendsListException;
import com.paymybuddy.paymybuddy.models.BankAccount;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.UsersService;
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
public class UsersServiceIT {

    @Autowired
    UsersService usersService;

    @Autowired
    UsersRepository usersRepository;

    UsersDTO usersDTO = new UsersDTO();
    UsersDTO usersDTO2 = new UsersDTO();
    List<UsersMinimalsInfoDTO> usersMinimalsInfoDTOList = new ArrayList<>();
    List<UsersDTO> usersDTOList = new ArrayList<>();

    BankAccount bankAccount = new BankAccount();
    List<BankAccount> bankAccountList = new ArrayList();

    BankAccountDTO bankAccountDTO = new BankAccountDTO();
    EmmetedTransactionDTO emmetedTransactionDTO = new EmmetedTransactionDTO();
    ReceivedTransactionDTO receivedTransactionDTO = new ReceivedTransactionDTO();
    TransfertDTO transfertDTO = new TransfertDTO();

    UsersMinimalsInfoDTO usersMinimalsInfoDTO = new UsersMinimalsInfoDTO();
    Users users = new Users();
    Users users2 = new Users();

    @BeforeEach
    public void setUp() {
        bankAccountDTO.setIban("1234");

        usersDTO2.setFirstName("Jack");
        usersDTO2.setLastName("Sparrow");
        usersDTO2.setEmail("jack@s.mail");
        usersDTO2.setTotalAmount(100.0);
        usersDTO2.setFriends(usersMinimalsInfoDTOList);

        usersDTOList.add(usersDTO);
        usersDTOList.add(usersDTO2);

        users.setId(1);
        users.setFirstName("John");
        users.setLastName("Doe");
        users.setEmail("john@doe.mail");
        users.setTotalAmount(100.0);
        users.setFriends(new ArrayList<>());

        users2.setId(2);
        users2.setFirstName("Jack");
        users2.setLastName("Sparrow");
        users2.setEmail("jack@s.mail");
        users2.setTotalAmount(100.0);
        users2.setFriends(new ArrayList<>());

        bankAccount.setId(1);
        bankAccount.setUsers(users);
        bankAccount.setActif(true);
        bankAccount.setBankName("DoeBank");
        bankAccount.setIban("01234");
        bankAccount.setDescription("leCompteDeJohn");
        bankAccountList.add(bankAccount);
        users.setBankAccounts(bankAccountList);

        usersMinimalsInfoDTO.setEmail(users.getEmail());
        usersMinimalsInfoDTO.setFirstName(users.getFirstName());
        usersMinimalsInfoDTO.setLastName(users.getLastName());

        bankAccountDTO.setBankName("DoeBank");
        bankAccountDTO.setActif(true);
        bankAccountDTO.setDescription("mon compte");

        emmetedTransactionDTO.setReceiver(usersMinimalsInfoDTO);
        emmetedTransactionDTO.setAmount(100.00);
        emmetedTransactionDTO.setDescription("ZeTest");
        emmetedTransactionDTO.setFee(0.5);

        receivedTransactionDTO.setAmount(50.00);
        receivedTransactionDTO.setDescription("merci");
        receivedTransactionDTO.setEmmeter(usersMinimalsInfoDTO);

        transfertDTO.setTransfertType(TransfertType.VIR_FROM_BANK_ACCOUNT.toString());
        transfertDTO.setAmount(100.00);
        transfertDTO.setDate(new Date());
        transfertDTO.setBankAccount(bankAccountDTO);

        usersDTO.setFirstName("John");
        usersDTO.setLastName("Doe");
        usersDTO.setEmail("john@doe.mail");
        usersDTO.setTotalAmount(100.0);
        usersDTO.setFriends(new ArrayList<>());
        usersDTO.setBankAccounts(new ArrayList<>());
        usersDTO.getBankAccounts().add(bankAccountDTO);
        usersDTO.setEmmetedTransactions(new ArrayList<>());
        usersDTO.getEmmetedTransactions().add(emmetedTransactionDTO);
        usersDTO.setReceivedTransactions(new ArrayList<>());
        usersDTO.getReceivedTransactions().add(receivedTransactionDTO);
        usersDTO.setTransferts(new ArrayList<>());
        usersDTO.getTransferts().add(transfertDTO);

        usersMinimalsInfoDTOList.add(usersMinimalsInfoDTO);
    }

    @Test
    @DisplayName("test testSubscribe Success")
    void testSubscribe() throws ExistingEmailException {

        UsersSubscribeDTO usersSubscribeDTO = new UsersSubscribeDTO();
        usersSubscribeDTO.setEmail("john@doe.mail");
        usersSubscribeDTO.setFirstName("John");
        usersSubscribeDTO.setLastName("Doe");
        usersSubscribeDTO.setPassword("toto");

        UserSubscribeOkDTO userSubscribeOkDTO = usersService.subscribe(usersSubscribeDTO);

        Assertions.assertEquals(usersSubscribeDTO.getFirstName(), userSubscribeOkDTO.getFirstName());

        Assertions.assertTrue(usersRepository.findById(users.getId()).isPresent());
        Assertions.assertEquals(usersSubscribeDTO.getEmail(), usersService.findById(1L).getEmail());

    }

    @Test
    @DisplayName("test usersFindAll Succes")
    void testUsersFindAll() throws ExistingEmailException {

        UsersSubscribeDTO usersSubscribeDTO = new UsersSubscribeDTO();
        usersSubscribeDTO.setEmail("john@doe.mail");
        usersSubscribeDTO.setFirstName("John");
        usersSubscribeDTO.setLastName("Doe");
        usersSubscribeDTO.setPassword("toto");

        UserSubscribeOkDTO userSubscribeOkDTO = usersService.subscribe(usersSubscribeDTO);

        List<UsersDTO> usersDTOList = usersService.findall();

        Assertions.assertEquals(userSubscribeOkDTO.getEmail(), usersDTOList.get(0).getEmail());
        Assertions.assertEquals(usersDTOList.size(), usersService.findall().size());

    }

    @Test
    @DisplayName("test usersAddFriends Succes")
    void testUsersAddFriends() throws ExistingEmailException, UserAllReadyExistException, UsersNotFoundException {

        UsersSubscribeDTO usersSubscribeDTO = new UsersSubscribeDTO();
        usersSubscribeDTO.setEmail("john@doe.mail");
        usersSubscribeDTO.setFirstName("John");
        usersSubscribeDTO.setLastName("Doe");
        usersSubscribeDTO.setPassword("toto");

        UsersSubscribeDTO usersSubscribeDTO2 = new UsersSubscribeDTO();
        usersSubscribeDTO2.setFirstName("Jack");
        usersSubscribeDTO2.setLastName("Sparrow");
        usersSubscribeDTO2.setEmail("jack@s.mail");
        usersSubscribeDTO2.setPassword("toto");

        UserSubscribeOkDTO userSubscribeOkDTO1 = usersService.subscribe(usersSubscribeDTO);
        UserSubscribeOkDTO userSubscribeOkDTO2 = usersService.subscribe(usersSubscribeDTO2);

        UsersMinimalsInfoDTO userAddFriend = usersService.addFriends(2L, usersMinimalsInfoDTO.getEmail());

        Assertions.assertEquals(userSubscribeOkDTO2.getFirstName(), userAddFriend.getFirstName());
    }

    @Test
    @DisplayName("test RemoveFriends Succes")
    void testUsersRemoveFriends() throws ExistingEmailException, UserAllReadyExistException, UsersNotFoundException, UsersNotInFriendsListException {

        UsersSubscribeDTO usersSubscribeDTO = new UsersSubscribeDTO();
        usersSubscribeDTO.setEmail("john@doe.mail");
        usersSubscribeDTO.setFirstName("John");
        usersSubscribeDTO.setLastName("Doe");
        usersSubscribeDTO.setPassword("toto");

        UsersSubscribeDTO usersSubscribeDTO2 = new UsersSubscribeDTO();
        usersSubscribeDTO2.setFirstName("Jack");
        usersSubscribeDTO2.setLastName("Sparrow");
        usersSubscribeDTO2.setEmail("jack@s.mail");
        usersSubscribeDTO2.setPassword("toto");

        UserSubscribeOkDTO userSubscribeOkDTO1 = usersService.subscribe(usersSubscribeDTO);
        UserSubscribeOkDTO userSubscribeOkDTO2 = usersService.subscribe(usersSubscribeDTO2);

        UsersMinimalsInfoDTO userAddFriend = usersService.addFriends(2L, usersMinimalsInfoDTO.getEmail());
        UsersDTO userRemoveFriend = usersService.removeFriends(2L, usersMinimalsInfoDTO.getEmail());

        Assertions.assertEquals(0,userRemoveFriend.getFriends().size());
    }


    @Test
    @DisplayName("test usersFindById Succes")
    void testUsersFindById() throws ExistingEmailException {

        UsersSubscribeDTO usersSubscribeDTO = new UsersSubscribeDTO();
        usersSubscribeDTO.setEmail("john@doe.mail");
        usersSubscribeDTO.setFirstName("John");
        usersSubscribeDTO.setLastName("Doe");
        usersSubscribeDTO.setPassword("toto");

        UserSubscribeOkDTO userSubscribeOkDTO1 = usersService.subscribe(usersSubscribeDTO);

        Users userAddFriend = usersService.findById(1L);

        Assertions.assertEquals(userSubscribeOkDTO1.getFirstName(), userAddFriend.getFirstName());
    }

    @Test
    @DisplayName("test usersUpdate Succes")
    void testUsersUpdate() throws ExistingEmailException, UsersNotFoundException {

        UsersSubscribeDTO usersSubscribeDTO = new UsersSubscribeDTO();
        usersSubscribeDTO.setEmail("john@doe.mail");
        usersSubscribeDTO.setFirstName("John");
        usersSubscribeDTO.setLastName("Doe");
        usersSubscribeDTO.setPassword("toto");

        UserSubscribeOkDTO userSubscribeOkDTO1 = usersService.subscribe(usersSubscribeDTO);
        usersDTO.setEmail("newEmail@update.com");

        UsersDTO userAddFriend = usersService.update(usersDTO, 1L);

        Assertions.assertEquals(userSubscribeOkDTO1.getEmail(), userAddFriend.getEmail());
    }

    @Test
    @DisplayName("test usersDelete Succes")
    void testUsersDelete() throws ExistingEmailException, UsersNotFoundException {

        UsersSubscribeDTO usersSubscribeDTO = new UsersSubscribeDTO();
        usersSubscribeDTO.setEmail("john@doe.mail");
        usersSubscribeDTO.setFirstName("John");
        usersSubscribeDTO.setLastName("Doe");
        usersSubscribeDTO.setPassword("toto");

        UserSubscribeOkDTO userSubscribeOkDTO1 = usersService.subscribe(usersSubscribeDTO);
        usersDTO.setEmail("newEmail@update.com");

        Long deletedUser= usersService.deleteById(1L);

        Assertions.assertEquals(1, deletedUser);
    }



}
