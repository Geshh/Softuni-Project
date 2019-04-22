package com.lulchev.busticketreservation.domain.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusAddBindingModel {
    private Integer capacity;
    private String brand;
    private String registrationNumber;
}
