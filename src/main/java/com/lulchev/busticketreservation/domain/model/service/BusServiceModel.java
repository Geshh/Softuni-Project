package com.lulchev.busticketreservation.domain.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BusServiceModel extends BaseServiceModel {
    @NotEmpty
    @NotNull
    private String brand;
    @NotEmpty
    @NotNull
    private String registrationNumber;
    @NotEmpty
    @NotNull
    private Integer capacity;


}
