package com.lulchev.busticketreservation.controller;

import com.lulchev.busticketreservation.domain.entitiy.Bus;
import com.lulchev.busticketreservation.repository.BusRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BusControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private BusRepository busRepository;

    @Before
    public void emptyDb() {
        this.busRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void create_returnsCorrectView() throws Exception {
        this.mvc.perform(get("/buses/create"))
                .andExpect(view().name("create_bus"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void create_savesEntityCorrectly() throws Exception {
        this.mvc.perform(
                post("/buses/create")
                        .param("capacity", "123")
                        .param("brand", "brand")
                        .param("registrationNumber", "123"));

        Bus bus = this.busRepository.findAll().get(0);
        Assert.assertEquals(1, this.busRepository.count());
        Assert.assertEquals("123", String.valueOf(bus.getCapacity()));
        Assert.assertEquals("brand", bus.getBrand());
        Assert.assertEquals("123", bus.getRegistrationNumber());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void create_redirectsCorrectly() throws Exception {
        this.mvc.perform(
                post("/buses/create")
                        .param("capacity", "123")
                        .param("brand", "brand")
                        .param("registrationNumber", "123"))
                .andExpect(redirectedUrl("/buses"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void edit_editsEntityCorrectly() throws Exception {
        Bus bus = new Bus();
        bus.setBrand("brand");
        bus.setCapacity(123);
        bus.setRegistrationNumber("123");

        bus = busRepository.saveAndFlush(bus);

        this.mvc.perform(
                put("/buses/edit/" + bus.getId())
                        .param("brand", "brand_edit")
                        .param("registrationNumber", "321")
                        .param("capacity", "321"));

        Bus busActual = this.busRepository.findAll().get(0);

        Assert.assertEquals(1, this.busRepository.count());
        Assert.assertEquals("321", String.valueOf(busActual.getCapacity()));
        Assert.assertEquals("brand_edit", busActual.getBrand());
        Assert.assertEquals("321", busActual.getRegistrationNumber());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void delete_deletesEntityCorrectly() throws Exception {
        Bus bus = new Bus();
        bus.setBrand("brand");
        bus.setCapacity(123);
        bus.setRegistrationNumber("123");

        bus = busRepository.saveAndFlush(bus);

        this.mvc.perform(
                delete("/buses/delete/" + bus.getId()));

        Assert.assertEquals(0, this.busRepository.count());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void fetchAll_returnsCorrectView() throws Exception {
        this.mvc.perform(
                get("/buses"))
                .andExpect(view().name("all_buses"));
    }
}
