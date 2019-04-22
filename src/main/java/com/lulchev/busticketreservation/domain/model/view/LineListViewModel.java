package com.lulchev.busticketreservation.domain.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LineListViewModel extends BaseViewModel {

    private String from;
    private String to;
}
