package com.lulchev.busticketreservation.domain.entitiy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "buses")
public class Bus extends BaseEntity {

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "brand")
    private String brand;

    @Column(name = "regNumber", nullable = false)
    private String registrationNumber;

}
