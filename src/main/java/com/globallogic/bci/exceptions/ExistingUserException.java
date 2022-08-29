package com.globallogic.bci.exceptions;

public class ExistingUserException extends RuntimeException {

    public ExistingUserException() {
        super("The user already exists");
    }

}
