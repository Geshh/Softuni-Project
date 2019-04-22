package com.lulchev.busticketreservation.domain.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusListViewModel extends BaseViewModel{
    private Integer capacity;
    private String brand;
    private String registrationNumber;
}
