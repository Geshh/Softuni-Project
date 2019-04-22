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
@Table(name = "lines")
public class Line extends BaseEntity {
    @Column(name = "from_dest", nullable = false)
    private String from;

    @Column(name = "to_dest", nullable = false)
    private String to;
}
