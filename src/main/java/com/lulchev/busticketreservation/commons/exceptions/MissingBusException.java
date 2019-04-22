package com.lulchev.busticketreservation.commons.exceptions;

public class MissingBusException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MissingBusException() {
    }

    public MissingBusException(String message) {
        super(message);
    }
}
