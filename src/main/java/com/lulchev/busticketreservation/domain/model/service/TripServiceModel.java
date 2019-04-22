package com.lulchev.busticketreservation.domain.model.service;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TripServiceModel extends BaseServiceModel {

    private LineServiceModel line;

    private BusServiceModel bus;

    private BigDecimal price;

    private LocalDate dateTimeOfDeparture;

    private Integer seatsAvailable;

    private Integer seatsReserved;
}
