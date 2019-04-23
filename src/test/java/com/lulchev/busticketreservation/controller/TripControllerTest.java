package com.lulchev.busticketreservation.controller;

import com.lulchev.busticketreservation.domain.entitiy.Bus;
import com.lulchev.busticketreservation.domain.entitiy.Line;
import com.lulchev.busticketreservation.domain.entitiy.Trip;
import com.lulchev.busticketreservation.repository.BusRepository;
import com.lulchev.busticketreservation.repository.LineRepository;
import com.lulchev.busticketreservation.repository.TripRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TripControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private BusRepository busRepository;


    @Before
    public void emptyDb() {
        this.tripRepository.deleteAll();
        this.lineRepository.deleteAll();
        this.busRepository.deleteAll();
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void create_returnsCorrectView() throws Exception {
        this.mvc.perform(get("/trips/create"))
                .andExpect(view().name("create_trip"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void create_savesEntityCorrectly() throws Exception {
        Line line = new Line();
        line.setFrom("from");
        line.setTo("to");
        lineRepository.saveAndFlush(line);

        Bus bus = new Bus();
        bus.setRegistrationNumber("123");
        bus.setCapacity(123);
        bus.setBrand("brand");
        busRepository.saveAndFlush(bus);

        this.mvc.perform(
                post("/trips/create")
                        .param("line", line.getId())
                        .param("bus", bus.getId())
                        .param("price", "123")
                        .param("dateTimeOfDeparture", String.valueOf(LocalDate.now())));

        Trip trip = this.tripRepository.findAll().get(0);
        Assert.assertEquals(1, this.tripRepository.count());
        Assert.assertEquals(Integer.valueOf(0), trip.getSeatsReserved());
        Assert.assertEquals(bus.getCapacity(), trip.getSeatsAvailable());
        Assert.assertEquals("123.00", trip.getPrice().toString());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void create_redirectsCorrectly() throws Exception {
        Line line = new Line();
        line.setFrom("from");
        line.setTo("to");
        lineRepository.saveAndFlush(line);

        Bus bus = new Bus();
        bus.setRegistrationNumber("123");
        bus.setCapacity(123);
        bus.setBrand("brand");
        busRepository.saveAndFlush(bus);

        this.mvc.perform(
                post("/trips/create")
                        .param("line", line.getId())
                        .param("bus", bus.getId())
                        .param("price", "123")
                        .param("dateTimeOfDeparture", String.valueOf(LocalDate.now())))
                .andExpect(redirectedUrl("/trips?favicon=https%3A%2F%2Fwww.download3k.de%2Ficons%2FBus-Driver-199713.png"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void edit_editsEntityCorrectly() throws Exception {
        Line line = new Line();
        line.setFrom("from");
        line.setTo("to");
        lineRepository.saveAndFlush(line);

        Bus bus = new Bus();
        bus.setRegistrationNumber("123");
        bus.setCapacity(123);
        bus.setBrand("brand");
        busRepository.saveAndFlush(bus);

        Trip trip = new Trip();
        trip.setBus(bus);
        trip.setSeatsReserved(0);
        trip.setSeatsAvailable(bus.getCapacity());
        trip.setDateTimeOfDeparture(LocalDate.now());
        trip.setLine(line);
        trip.setPrice(BigDecimal.valueOf(132));

        trip = tripRepository.saveAndFlush(trip);

        this.mvc.perform(
                put("/trips/edit/" + trip.getId())
                        .param("line", line.getId())
                        .param("bus", bus.getId())
                        .param("price", "321")
                        .param("dateTimeOfDeparture", String.valueOf(LocalDate.now()))
                        .param("seatsAvailable", "123")
                        .param("seatsReserved", "1"));

        Trip tripActual = tripRepository.findAll().get(0);

        Assert.assertEquals(1, this.tripRepository.count());
        Assert.assertEquals(Integer.valueOf(1), tripActual.getSeatsReserved());
        Assert.assertEquals("321.00", tripActual.getPrice().toString());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void delete_deletesEntityCorrectly() throws Exception {
        Line line = new Line();
        line.setFrom("from");
        line.setTo("to");
        lineRepository.saveAndFlush(line);

        Bus bus = new Bus();
        bus.setRegistrationNumber("123");
        bus.setCapacity(123);
        bus.setBrand("brand");
        busRepository.saveAndFlush(bus);

        Trip trip = new Trip();
        trip.setBus(bus);
        trip.setSeatsReserved(0);
        trip.setSeatsAvailable(bus.getCapacity());
        trip.setDateTimeOfDeparture(LocalDate.now());
        trip.setLine(line);
        trip.setPrice(BigDecimal.valueOf(132));

        trip = tripRepository.saveAndFlush(trip);

        this.mvc.perform(
                delete("/trips/delete/" + trip.getId()));

        Assert.assertEquals(0, this.tripRepository.count());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void fetchAll_returnsCorrectView() throws Exception {
        this.mvc.perform(
                get("/trips"))
                .andExpect(view().name("all_trips"));
    }
}
