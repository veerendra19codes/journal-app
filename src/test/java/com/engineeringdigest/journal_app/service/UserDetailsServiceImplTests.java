package com.engineeringdigest.journal_app.service;

import com.engineeringdigest.journal_app.entity.UserEntity;
import com.engineeringdigest.journal_app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

//in this version we are loading entire spring context to load UserDetailsServiceImpl and
//then mocking just the UserRepository which would be then outside the spring context
//@SpringBootTest
//public class UserDetailsServiceImplTests {
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsServiceImpl;
//
//    @MockitoBean
//    private UserRepository userRepository;
//
//    @Test
//    void testLoadUserByUsername() {
//        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(UserEntity.builder().userName("ram").password("ram").roles(new ArrayList<>()).build());
//        UserDetails user = userDetailsServiceImpl.loadUserByUsername("ram");
//        assertNotNull(user);
//    }
//}

//in this version, we inject the service using injectMocks which initializes the mocked service
// and mock the repository using Mock , to initialize it we have use MockitoAnnotations.injectMocks
// now everytime we run this class file spring context is not loaded and time and space is saved
public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLoadUserByUsername() {
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(UserEntity.builder().userName("ram").password("ram").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsServiceImpl.loadUserByUsername("ram");
        assertNotNull(user);
    }
}
