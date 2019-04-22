package com.lulchev.busticketreservation.domain.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusEditBindingModel extends BaseBindingModel {
    private Integer capacity;
    private String brand;
    private String registrationNumber;
}
