package com.lulchev.busticketreservation.domain.entitiy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "trips")
public class Trip extends BaseEntity {

    @ManyToOne(targetEntity = Line.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "line_id", referencedColumnName = "id")
    private Line line;

    @ManyToOne(targetEntity = Bus.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "bus_id", referencedColumnName = "id")
    private Bus bus;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "date_departure")
    private LocalDate dateTimeOfDeparture;

    @Column(name = "seats_available")
    private Integer seatsAvailable;

    @Column(name = "seats_reserved")
    private Integer seatsReserved;
}
