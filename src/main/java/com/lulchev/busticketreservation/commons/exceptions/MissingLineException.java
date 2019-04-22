package com.lulchev.busticketreservation.commons.exceptions;

public class MissingLineException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MissingLineException() {
    }

    public MissingLineException(String message) {
        super(message);
    }
}
