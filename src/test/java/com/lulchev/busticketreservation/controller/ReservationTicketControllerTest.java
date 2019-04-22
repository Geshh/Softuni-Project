package com.lulchev.busticketreservation.controller;

import com.lulchev.busticketreservation.domain.entitiy.Bus;
import com.lulchev.busticketreservation.domain.entitiy.Line;
import com.lulchev.busticketreservation.domain.entitiy.ReservationTicket;
import com.lulchev.busticketreservation.domain.entitiy.Trip;
import com.lulchev.busticketreservation.domain.model.binding.UserRegisterBindingModel;
import com.lulchev.busticketreservation.domain.model.service.UserServiceModel;
import com.lulchev.busticketreservation.repository.*;
import com.lulchev.busticketreservation.service.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReservationTicketControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ReservationTicketRepository reservationTicketRepository;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @Before
    public void emptyDb() {
        this.reservationTicketRepository.deleteAll();
        this.tripRepository.deleteAll();
        this.busRepository.deleteAll();
        this.lineRepository.deleteAll();
        this.userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void buyTicket_ReturnsCorrectView() throws Exception {
        this.mvc.perform(get("/tickets/buy"))
                .andExpect(view().name("buy_tickets"));
    }

    @Test
    @WithMockUser(username = "username", roles = "ADMIN")
    public void buyTicket_savesEntityCorrectly() throws Exception {
        Line line = new Line();
        line.setFrom("from");
        line.setTo("to");
        line = lineRepository.saveAndFlush(line);

        Bus bus = new Bus();
        bus.setRegistrationNumber("123");
        bus.setCapacity(123);
        bus.setBrand("brand");
        bus = busRepository.saveAndFlush(bus);

        Trip trip = new Trip();
        trip.setBus(bus);
        trip.setSeatsReserved(0);
        trip.setSeatsAvailable(bus.getCapacity());
        trip.setDateTimeOfDeparture(LocalDate.now());
        trip.setLine(line);
        trip.setPrice(BigDecimal.valueOf(132));
        trip = tripRepository.saveAndFlush(trip);

        registerUser();

        this.mvc.perform(
                put("/tickets/buy/" + trip.getId()));

        Assert.assertEquals(1, this.reservationTicketRepository.count());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void delete_deletesEntityCorrectly() throws Exception {
        Line line = new Line();
        line.setFrom("from");
        line.setTo("to");
        line = lineRepository.saveAndFlush(line);

        Bus bus = new Bus();
        bus.setRegistrationNumber("123");
        bus.setCapacity(123);
        bus.setBrand("brand");
        bus = busRepository.saveAndFlush(bus);

        Trip trip = new Trip();
        trip.setBus(bus);
        trip.setSeatsReserved(0);
        trip.setSeatsAvailable(bus.getCapacity());
        trip.setDateTimeOfDeparture(LocalDate.now());
        trip.setLine(line);
        trip.setPrice(BigDecimal.valueOf(132));
        trip = tripRepository.saveAndFlush(trip);

        registerUser();

        ReservationTicket reservationTicket = new ReservationTicket();
        reservationTicket.setSeat(5);
        reservationTicket.setTrip(trip);
        reservationTicket.setUser(userRepository.findByUsername("username").get());

        reservationTicket = reservationTicketRepository.saveAndFlush(reservationTicket);

        this.mvc.perform(
                delete("/tickets/delete/" + reservationTicket.getId()));

        Assert.assertEquals(0, this.reservationTicketRepository.count());
    }

    private void registerUser() {
        final UserRegisterBindingModel userRegisterBindingModel = new UserRegisterBindingModel();
        userRegisterBindingModel.setUsername("username");
        userRegisterBindingModel.setConfirmPassword("123");
        userRegisterBindingModel.setPassword("123");
        userRegisterBindingModel.setMail("mail@abv.bg");

        userService.register(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void fetchAll_returnsCorrectView() throws Exception {
        this.mvc.perform(
                get("/tickets"))
                .andExpect(view().name("all_tickets"));
    }
}
