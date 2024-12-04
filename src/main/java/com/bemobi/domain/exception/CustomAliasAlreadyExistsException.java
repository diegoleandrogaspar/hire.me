package com.bemobi.domain.exception;

public class CustomAliasAlreadyExistsException extends RuntimeException {

    public CustomAliasAlreadyExistsException(String message) {
        super(message);
    }

    public CustomAliasAlreadyExistsException() {
        super(String.format("ERR_CODE: 001, Description:CUSTOM ALIAS ALREADY EXISTS}"));
    }
}
