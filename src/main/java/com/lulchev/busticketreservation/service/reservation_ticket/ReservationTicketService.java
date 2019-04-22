package com.lulchev.busticketreservation.service.reservation_ticket;

import com.lulchev.busticketreservation.domain.model.service.ReservationTicketServiceModel;

import java.util.List;

public interface ReservationTicketService {
    List<ReservationTicketServiceModel> findAll();

    List<ReservationTicketServiceModel> findAllForUserByUsername(String username);

    void createTicketForUser(String tripId, String username);

    void edit(String id, ReservationTicketServiceModel reservationTicketServiceModel);

    void delete(String id);
}
