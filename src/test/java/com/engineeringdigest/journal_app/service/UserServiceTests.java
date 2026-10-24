package com.engineeringdigest.journal_app.service;

import com.engineeringdigest.journal_app.entity.UserEntity;
import com.engineeringdigest.journal_app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    enum TestUser {
        ram, sham
    }

    @ParameterizedTest
    @EnumSource(TestUser.class)
    void getByUserNameReturnsMatchingUser(TestUser testUser) {
        UserEntity expectedUser = UserEntity.builder()
                .userName(testUser.name())
                .password("password")
                .build();
        when(userRepository.findByUserName(testUser.name())).thenReturn(expectedUser);

        UserEntity actualUser = userService.getByUserName(testUser.name());

        assertSame(expectedUser, actualUser);
        verify(userRepository).findByUserName(testUser.name());
    }
}
