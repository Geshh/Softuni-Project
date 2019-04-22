package com.lulchev.busticketreservation.service.reservation_ticket;

import com.lulchev.busticketreservation.commons.exceptions.MissingReservationTicketException;
import com.lulchev.busticketreservation.commons.exceptions.MissingUserException;
import com.lulchev.busticketreservation.domain.entitiy.ReservationTicket;
import com.lulchev.busticketreservation.domain.entitiy.Trip;
import com.lulchev.busticketreservation.domain.entitiy.User;
import com.lulchev.busticketreservation.domain.model.service.ReservationTicketServiceModel;
import com.lulchev.busticketreservation.domain.model.service.TripServiceModel;
import com.lulchev.busticketreservation.domain.model.service.UserServiceModel;
import com.lulchev.busticketreservation.repository.ReservationTicketRepository;
import com.lulchev.busticketreservation.repository.TripRepository;
import com.lulchev.busticketreservation.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ReservationTicketServiceImpl implements ReservationTicketService {
    private final ReservationTicketRepository reservationTicketRepository;
    private final ModelMapper modelMapper;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReservationTicketServiceImpl(ReservationTicketRepository reservationTicketRepository, ModelMapper modelMapper, TripRepository tripRepository, UserRepository userRepository) {
        this.reservationTicketRepository = reservationTicketRepository;
        this.modelMapper = modelMapper;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ReservationTicketServiceModel> findAll() {
        return reservationTicketRepository.findAll().stream()
                .map(ticket -> modelMapper.map(ticket, ReservationTicketServiceModel.class))
                .collect(toList());
    }

    @Override
    public List<ReservationTicketServiceModel> findAllForUserByUsername(String username) {
        final Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            List<ReservationTicket> reservationTickets = reservationTicketRepository.findAllByUser(user);
            return reservationTickets.stream().map(ticket -> modelMapper.map(ticket, ReservationTicketServiceModel.class))
                    .collect(toList());
        } else {
            throw new MissingUserException("Unable to find user by username");
        }
    }

    @Override
    public void createTicketForUser(String tripId, String username) {
        Optional<Trip> tripOptional = tripRepository.findById(tripId);
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (tripOptional.isPresent() && userOptional.isPresent()) {
            Trip trip = tripOptional.get();
            User user = userOptional.get();
            trip.setSeatsAvailable(trip.getSeatsAvailable() - 1);
            trip.setSeatsReserved(trip.getSeatsReserved() + 1);

            ReservationTicket reservationTicket = new ReservationTicket();
            reservationTicket.setUser(user);
            reservationTicket.setTrip(trip);
            reservationTicket.setSeat(trip.getSeatsReserved());

            tripRepository.saveAndFlush(trip);
            reservationTicketRepository.saveAndFlush(reservationTicket);
        }
    }

    @Override
    public void edit(String id, ReservationTicketServiceModel reservationTicketServiceModel) {
        final ReservationTicket reservationTicket = reservationTicketRepository.findById(id)
                .orElseThrow(() -> new MissingReservationTicketException("Unable to find ticket by id"));
        reservationTicketRepository.saveAndFlush(modelMapper.map(reservationTicketServiceModel, ReservationTicket.class));
    }

    @Override
    public void delete(String id) {
        reservationTicketRepository.deleteById(id);
    }
}
