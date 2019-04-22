package com.lulchev.busticketreservation.domain.model.view;

import com.lulchev.busticketreservation.domain.model.service.TripServiceModel;
import com.lulchev.busticketreservation.domain.model.service.UserServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationTicketListViewModel extends BaseViewModel {
    private UserServiceModel user;

    private TripServiceModel trip;

    private Integer seat;
}
