package com.lulchev.busticketreservation.domain.entitiy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tickets")
public class ReservationTicket extends BaseEntity {

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(targetEntity = Trip.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip;

    @Column
    private Integer seat;
}
