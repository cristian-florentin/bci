package com.globallogic.bci.exceptions;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException() {
        super("The inserted Token is not a valid one or the User doesn't exists.");
    }

}
