package com.lulchev.busticketreservation.controller;

import com.lulchev.busticketreservation.domain.entitiy.Line;
import com.lulchev.busticketreservation.repository.LineRepository;
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
public class LinesControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private LineRepository lineRepository;

    @Before
    public void emptyDb() {
        this.lineRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void create_returnsCorrectView() throws Exception {
        this.mvc.perform(get("/lines/create"))
                .andExpect(view().name("create_line"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void create_savesEntityCorrectly() throws Exception {
        this.mvc.perform(
                post("/lines/create")
                        .param("from", "from_dest")
                        .param("to", "to_dest"));

        Line line = this.lineRepository.findAll().get(0);
        Assert.assertEquals(1, this.lineRepository.count());
        Assert.assertEquals("from_dest", line.getFrom());
        Assert.assertEquals("to_dest", line.getTo());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void create_redirectsCorrectly() throws Exception {
        this.mvc.perform(
                post("/lines/create")
                        .param("from", "from_dest")
                        .param("to", "to_dest"))
                .andExpect(redirectedUrl("/lines"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void edit_editsEntityCorrectly() throws Exception {
        Line line = new Line();
        line.setFrom("from");
        line.setTo("to");

        line = lineRepository.saveAndFlush(line);

        this.mvc.perform(
                put("/lines/edit/" + line.getId())
                        .param("from", "from_edit")
                        .param("to", "to_edit"));

        Line lineActual = this.lineRepository.findAll().get(0);

        Assert.assertEquals(1, this.lineRepository.count());
        Assert.assertEquals("from_edit", lineActual.getFrom());
        Assert.assertEquals("to_edit", lineActual.getTo());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void delete_deletesEntityCorrectly() throws Exception {
        Line line = new Line();
        line.setFrom("from");
        line.setTo("to");

        line = lineRepository.saveAndFlush(line);

        this.mvc.perform(
                delete("/lines/delete/" + line.getId()));

        Assert.assertEquals(0, this.lineRepository.count());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void fetchAll_returnsCorrectView() throws Exception {
        this.mvc.perform(
                get("/lines"))
                .andExpect(view().name("all_lines"));
    }
}
