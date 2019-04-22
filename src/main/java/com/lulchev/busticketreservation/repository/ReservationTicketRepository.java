package com.lulchev.busticketreservation.repository;

import com.lulchev.busticketreservation.domain.entitiy.ReservationTicket;
import com.lulchev.busticketreservation.domain.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationTicketRepository extends JpaRepository<ReservationTicket, String> {
    List<ReservationTicket> findAllByUser(User user);
}
