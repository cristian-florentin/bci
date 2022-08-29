package com.globallogic.bci.service;

import com.globallogic.bci.dto.UserPayload;
import com.globallogic.bci.dto.UserResponse;
import com.globallogic.bci.entity.User;
import com.globallogic.bci.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Mock
    @Autowired
    private UserRepository userRepository;



    @Test
    public void signUpUserTest() throws Exception {
        when(userRepository.save(any(User.class))).thenReturn(generateUser());
        UserResponse response = userService.signUpUser(generateUserPayload());
        assertNotNull(response);
    }

    private UserPayload generateUserPayload(){
        UserPayload userPayload = new UserPayload();
        userPayload.setName("test");
        userPayload.setEmail("test@test.com");
        userPayload.setPassword("Password1Test1");
        return userPayload;
    }

    private User generateUser(){
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("test");
        user.setEmail("test@test.com");
        user.setPassword("Password1Test1");
        user.setActive(true);
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        return user;
    }

}
