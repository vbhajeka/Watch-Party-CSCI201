package com.alex.springbaeldung.controllers;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
