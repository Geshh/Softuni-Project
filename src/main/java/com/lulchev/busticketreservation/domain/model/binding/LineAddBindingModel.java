package com.lulchev.busticketreservation.domain.model.binding;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LineAddBindingModel {
    private String from;
    private String to;
}
