package com.lulchev.busticketreservation.repository;

import com.lulchev.busticketreservation.domain.entitiy.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus, String> {

}
