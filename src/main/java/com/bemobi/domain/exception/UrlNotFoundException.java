package com.bemobi.domain.exception;

public class UrlNotFoundException extends RuntimeException{

    public UrlNotFoundException(String alias) {
        super(String.format("ERR_CODE: 002, Description: SHORTENED URL NOT FOUND for alias '%s' ", alias));
    }
}

