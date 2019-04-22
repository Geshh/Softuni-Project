package com.lulchev.busticketreservation.domain.model.view;

import com.lulchev.busticketreservation.domain.model.service.BusServiceModel;
import com.lulchev.busticketreservation.domain.model.service.LineServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TripListViewModel extends BaseViewModel {
    private LineServiceModel line;

    private BusServiceModel bus;

    private BigDecimal price;

    private LocalDate dateTimeOfDeparture;

    private Integer seatsAvailable;

    private Integer seatsReserved;
}
