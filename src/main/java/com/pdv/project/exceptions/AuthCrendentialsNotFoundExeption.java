package com.pdv.project.exceptions;

public class AuthCrendentialsNotFoundExeption extends Exception {

    public AuthCrendentialsNotFoundExeption() {
        super();
    }

    public AuthCrendentialsNotFoundExeption(String message) {
        super(message);
    }

    public AuthCrendentialsNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthCrendentialsNotFoundExeption(Throwable cause) {
        super(cause);
    }

}