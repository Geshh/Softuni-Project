package com.lulchev.busticketreservation.commons.exceptions;

public class MissingReservationTicketException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MissingReservationTicketException() {
    }

    public MissingReservationTicketException(String message) {
        super(message);
    }
}
