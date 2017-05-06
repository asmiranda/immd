package com.pccw.immd.exception;

/**
 * Created by vagrant on 5/3/17.
 */
public class InvalidCredentialException extends IllegalArgumentException {

    public InvalidCredentialException(String message) {
        super(message);
    }

}
