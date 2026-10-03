package com.engineeringdigest.journal_app.service;

import com.engineeringdigest.journal_app.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

//    before each method
    @BeforeEach
    public void setup() {}

//    before all of the methods like defining global variables
    @BeforeAll
    public static void init() {}

    @AfterAll
    public static void cleanup() {}

    @AfterEach
    public void tearDown() {}

    @Disabled
    @Test
    public void userServiceTests() {
        assertEquals(4, 1+3);
        assertNotNull(userRepository.findByUserName("ram"));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    public void testAdd(int a, int b, int expected) {
        assertEquals(expected, a+b);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "ram",
            "sham"
    })
    public void testFindByUserNameParameterized(String username) {
        assertNotNull(userRepository.findByUserName(username), "Failed for username: " + username);
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
            "ram",
            "sham"
    })
    public void testFindByUserNameValueSource(String username) {
        assertNotNull(userRepository.findByUserName(username), "Failed for username: " + username);
    }

    enum TestUser {
        ram, sham
    }

    @ParameterizedTest
    @EnumSource(TestUser.class)
    public void testFindByUserNameEnumSource(TestUser user) {
        assertNotNull(userRepository.findByUserName(user.name()), "Failed for username: " + user.name());
    }

}
