package com.globallogic.bci.service;

import com.globallogic.bci.dto.LoggedInUserResponse;
import com.globallogic.bci.dto.UserPayload;
import com.globallogic.bci.dto.UserResponse;
import com.globallogic.bci.entity.User;
import com.globallogic.bci.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLDataException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final UUID uuid = UUID.randomUUID();

    private final String validToken = "eyJhbGciOiJIUzUxMiJ9." +
            "eyJqdGkiOiJiY2kiLCJzdWIiOiJ0ZXN0MkB0ZXN0LmNvbSIsImF1dGhvcml0aWVzIjpbI" +
            "lJPTEVfVVNFUiJdLCJpYXQiOjE2NjE4ODY3NDB9" +
            ".YDRQ8fkhiflvLeQhDfzy67rFMIgdWW9EC-nvOgb3v4NEsSsz51Dw-blcOQdAIFH2w_YLzYM8Z6w69noxHjSSxw";


    @Test
    public void signUpUserHappyPathTest() throws Exception {
        when(userRepository.save(any())).thenReturn(generateUser());
        UserResponse response = userService.signUpUser(generateUserPayload());
        UserResponse compare = generateUserResponse();
        compare.setToken(response.getToken());
        compare.setCreated(response.getCreated());
        assertEquals(response, compare);
    }

    @Test
    public void signUpUserNonExistingException() {
        when(userRepository.save(any())).thenReturn(new SQLDataException());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.signUpUser(generateUserPayload());
        });
        assertTrue(exception.getMessage().equals("The user already exists"));
    }

    @Test
    public void logInUserHappyPath() {
        when(userRepository.findByEmail(any())).thenReturn(generateUser());
        LoggedInUserResponse response = userService.logInUser(validToken);
        assertEquals(response.getEmail(), "test@test.com");
        assertEquals(response.getClass(), LoggedInUserResponse.class);
        assertEquals(response.getId(), uuid);
        assertNotEquals(response.getToken(), validToken);
    }

    @Test
    public void logInUserNonExistingException() {
        when(userRepository.findByEmail(any())).thenReturn(null);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.logInUser(validToken);
        });
        assertTrue(exception.getMessage().contains("the User doesn't exists"));
    }

    @Test
    public void logInUserInvalidTokenException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.logInUser("invalidToken");
        });
        assertTrue(exception.getMessage().contains("the User doesn't exists"));
    }


    private User generateUser(){
        User user = new User();
        user.setId(uuid);
        user.setName("test");
        user.setEmail("test@test.com");
        user.setPassword("Password1Test1");
        user.setActive(true);
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        return user;
    }

    private UserResponse generateUserResponse(){
        UserResponse user = new UserResponse();
        user.setId(uuid);
        user.setActive(true);
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        return user;
    }

    private UserPayload generateUserPayload(){
        UserPayload user = new UserPayload();
        user.setName("test");
        user.setEmail("test@test.com");
        user.setPassword("Password1Test1");
        return user;
    }

}
