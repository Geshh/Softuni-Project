package com.lulchev.busticketreservation.domain.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationTicketServiceModel extends BaseServiceModel {
    private UserServiceModel user;

    private TripServiceModel trip;

    private Integer seat;
}
