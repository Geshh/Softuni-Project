package com.lulchev.busticketreservation.repository;

import com.lulchev.busticketreservation.domain.entitiy.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepository extends JpaRepository<Line, String> {
}
