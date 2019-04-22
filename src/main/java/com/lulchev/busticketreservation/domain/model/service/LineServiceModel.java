package com.lulchev.busticketreservation.domain.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class LineServiceModel extends BaseServiceModel {

    @NotEmpty
    @NotNull
    private String from;
    @NotEmpty
    @NotNull
    private String to;
}
