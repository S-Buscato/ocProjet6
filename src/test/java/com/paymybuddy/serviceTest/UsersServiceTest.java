package com.paymybuddy.serviceTest;

import com.paymybuddy.paymybuddy.service.UsersService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@AllArgsConstructor
@SpringBootTest
@AutoConfigureTestDatabase
public class UsersTest {

    @Autowired
    final UsersService usersService;

    @Test
    @DisplayName("test Find by firstName & lastName Succes")
    void testFindbyFirstNameAndLastName(){
        usersService.findById(1);
    }

}

