package com.globallogic.bci.controller;

import com.globallogic.bci.dto.LogInPayload;
import com.globallogic.bci.dto.LoggedInUserResponse;
import com.globallogic.bci.dto.UserPayload;
import com.globallogic.bci.dto.UserResponse;
import com.globallogic.bci.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/sign-up",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody UserPayload user) throws Exception {
            return new ResponseEntity<>(userService.signUpUser(user), HttpStatus.CREATED);
    }

    @PostMapping(value = "/log-in",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<LoggedInUserResponse> logIn(@RequestBody LogInPayload logInPayload) throws Exception {
        return new ResponseEntity<>(userService.logInUser(logInPayload.getToken()), HttpStatus.OK);
    }

}
