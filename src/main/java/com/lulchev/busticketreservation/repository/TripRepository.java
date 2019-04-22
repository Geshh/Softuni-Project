package com.lulchev.busticketreservation.repository;

import com.lulchev.busticketreservation.domain.entitiy.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TripRepository extends JpaRepository<Trip, String> {
    void deleteAllByDateTimeOfDepartureIsBefore(LocalDate date);
}
