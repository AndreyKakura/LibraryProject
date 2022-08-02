package com.kakura.libraryproject.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("clown");
        user.setSurname("Ivan");
        user.setName("Ivanov");
        user.setEmail("clown@gmail.com");
        user.setPhone(new BigInteger("375441234567"));
        user.setUserRole(UserRole.USER);
        user.setUserStatus(UserStatus.ACTIVE);
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void testToString() {
        System.out.println(user);
    }
}