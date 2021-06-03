package com.paymybuddy.serviceTest;

import com.paymybuddy.paymybuddy.dto.UserSubscribeOkDTO;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.dto.UsersSubscribeDTO;
import com.paymybuddy.paymybuddy.exception.ExistingEmailException;
import com.paymybuddy.paymybuddy.exception.UserAllReadyExistException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.models.BankAccount;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.UsersService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = UsersService.class)
public class UsersServiceTest {

    @Autowired
    UsersService usersService;

    @MockBean
    UsersRepository usersRepository;

    UsersDTO usersDTO = new UsersDTO();
    UsersDTO usersDTO2 = new UsersDTO();
    List<UsersMinimalsInfoDTO> usersMinimalsInfoDTOList = new ArrayList<>();
    List<UsersDTO> usersDTOList = new ArrayList<>();

    BankAccount bankAccount = new BankAccount();
    List<BankAccount> bankAccountList = new ArrayList();

    UsersMinimalsInfoDTO usersMinimalsInfoDTO = new UsersMinimalsInfoDTO();
    Users users = new Users();
    Users users2 = new Users();

    @BeforeEach
    public void setUp() {
        usersDTO.setFirstName("John");
        usersDTO.setLastName("Doe");
        usersDTO.setEmail("john@doe.mail");
        usersDTO.setTotalAmount(100.0);
        usersDTO.setFriends(new ArrayList<>());

        usersMinimalsInfoDTOList.add(usersMinimalsInfoDTO);

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
    }

    @Test
    @DisplayName("test usersFindByAll Succes")
    void testUsersFindByAll(){

        List<Users> usersList = new ArrayList<>();
        usersList.add(users);

        when(usersRepository.findAll()).thenReturn(usersList);

        List<UsersDTO> usersDTOList = usersService.findall();
        Assertions.assertEquals(users.getEmail(), usersDTOList.get(0).getEmail());

        verify(usersRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("test usersFindById Succes")
    void testUsersFindById(){
        when(usersRepository.findById(1L)).thenReturn(Optional.ofNullable(users));

        Users usersResult = usersService.findById(1L);

        Assertions.assertEquals(users, usersResult );

        verify(usersRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("test usersFindByEmail Succes")
    void testUsersFindByEmail(){
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(users));

        Optional<Users> usersResult = usersService.findByEmail("JohnDoe@email.com");

        Assertions.assertEquals(users.getEmail(), usersResult.get().getEmail() );

        verify(usersRepository, times(1)).findByEmail(anyString());
    }
    @Test
    @DisplayName("test usersAddUserFriends Succes")
    void testUsersAddUserFriends() throws UserAllReadyExistException, UsersNotFoundException {

        usersMinimalsInfoDTO.setEmail(users.getEmail());
        usersMinimalsInfoDTO.setFirstName(users.getFirstName());
        usersMinimalsInfoDTO.setLastName(users.getLastName());

        when(usersRepository.findById(2L)).thenReturn(Optional.ofNullable(users2));
        when(usersRepository.findByEmail("john@doe.mail")).thenReturn(Optional.ofNullable(users));
        when(usersRepository.save(any(Users.class))).thenReturn(users);

        UsersMinimalsInfoDTO usersMinimalsInfoDTO2 = usersService.addFriends(2L,usersMinimalsInfoDTO);

        Assertions.assertEquals(usersMinimalsInfoDTO2.getEmail(), usersMinimalsInfoDTO.getEmail());

        verify(usersRepository, times(1)).findById(2L);
        verify(usersRepository, times(2)).findByEmail("john@doe.mail");
        verify(usersRepository, times(2)).save(any(Users.class));
    }

    @Test
    @DisplayName("test usersRemoveUserFriends Succes")
    void testUsersRemoveUserFriends() throws UsersNotFoundException {

        users2.getFriends().add(users);

        when(usersRepository.findById(2L)).thenReturn(Optional.ofNullable(users2));
        when(usersRepository.findByEmail("john@doe.mail")).thenReturn(Optional.ofNullable(users));
        when(usersRepository.save(any(Users.class))).thenReturn(users);

        UsersDTO userResponse = usersService.removeFriends(2L,usersMinimalsInfoDTO);

        Assertions.assertTrue(userResponse.getFriends().size() == 0);

        verify(usersRepository, times(2)).findById(2L);
        verify(usersRepository, times(2)).findByEmail("john@doe.mail");
        verify(usersRepository, times(2)).save(any(Users.class));
    }

    @Test
    @DisplayName("test subscribe Succes")
    void testSubscribe() throws ExistingEmailException {

        UsersSubscribeDTO usersSubscribeDTO = new UsersSubscribeDTO();
        usersSubscribeDTO.setEmail("john@doe.mail");
        usersSubscribeDTO.setFirstName("John");
        usersSubscribeDTO.setLastName("Doe");
        usersSubscribeDTO.setPassword("toto");

        when(usersRepository.findByEmail("john@doe.mail")).thenReturn(Optional.empty());
        when(usersRepository.save(any(Users.class))).thenReturn(users);

        UserSubscribeOkDTO userSubscribeOkDTO = usersService.subscribe(usersSubscribeDTO);

        Assertions.assertEquals("John", userSubscribeOkDTO.getFirstName());

        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    @DisplayName("test findUserInfo Succes")
    void testFindUserInfo() throws UsersNotFoundException {
        when(usersRepository.findById(anyLong())).thenReturn(Optional.ofNullable(users));

        UsersDTO usersDTOResponse= usersService.findUserInfo(1L);

        Assertions.assertEquals("John", usersDTOResponse.getFirstName());

        verify(usersRepository, times(2)).findById(anyLong());
    }

    @Test
    @DisplayName("test FindUserFriends Succes")
    void testFindUserFriends() throws UsersNotFoundException {
        users.getFriends().add(users2);
        when(usersRepository.findById(anyLong())).thenReturn(Optional.ofNullable(users));

        UsersMinimalsInfoDTO usersMinimalsInfoDTO= usersService.findUsersFriends(1L);

        Assertions.assertEquals(users.getEmail(), usersMinimalsInfoDTO.getEmail());

        verify(usersRepository, times(2)).findById(anyLong());
    }

    @Test
    @DisplayName("test FindUserFriends Succes")
    void testDeleteUsers() {
        usersService.deleteById(1L);
        verify(usersRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("test UdapteUsers Succes")
    void testUpdateUsers() throws UsersNotFoundException {
        users2.setId(1);
        when(usersRepository.findById(anyLong())).thenReturn(Optional.ofNullable(users));
        when(usersRepository.save(any(Users.class))).thenReturn(users2);

        UsersDTO usersResponse = usersService.update(usersDTO2, 1L);

        Assertions.assertEquals(usersDTO2.getEmail(), usersResponse.getEmail());

        verify(usersRepository, times(1)).findById(anyLong());
        verify(usersRepository, times(1)).save(any(Users.class));

    }

    @Test
    @DisplayName("test saveUsers Succes")
    void testSaveUsers() throws UsersNotFoundException {
        when(usersRepository.save(any(Users.class))).thenReturn(users);

        Users usersResponse = usersService.save(users);

        Assertions.assertEquals(usersResponse.getEmail(), users.getEmail());

        verify(usersRepository, times(1)).save(any(Users.class));

    }


}

