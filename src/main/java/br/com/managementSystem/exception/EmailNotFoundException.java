package br.com.managementSystem.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(String msg) {
        super(msg);
    }
}
