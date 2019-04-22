package com.lulchev.busticketreservation.commons.exceptions;

public class MissingTripException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MissingTripException() {
    }

    public MissingTripException(String message) {
        super(message);
    }
}
