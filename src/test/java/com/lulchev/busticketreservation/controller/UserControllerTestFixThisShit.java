package com.lulchev.busticketreservation.controller;

import com.lulchev.busticketreservation.domain.model.binding.UserRegisterBindingModel;
import com.lulchev.busticketreservation.domain.model.service.UserServiceModel;
import com.lulchev.busticketreservation.repository.UserRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTestFixThisShit {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @Before
    public void emptyDb() {
        this.userRepository.deleteAll();
        registerUser("root_user", "root_email@abv.bg");
    }

    @Test
    public void login_returnsCorrectView() throws Exception {
        this.mvc.perform(get("/users/login")).andExpect(view().name("login"));
    }

    @Test
    public void register_returnsCorrectView() throws Exception {
        this.mvc.perform(get("/users/register")).andExpect(view().name("register"));
    }

    @Test
    public void register_registersUserCorrectly() throws Exception {
        this.mvc.perform(
                post("/users/register")
                        .param("username", "pesho")
                        .param("password", "1")
                        .param("confirmPassword", "1")
                        .param("email", "p@p.p"));
        Assert.assertEquals(1, this.userRepository.count());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void fetchAll_ReturnsCorrectView() throws Exception {
        this.mvc.perform(
                get("/users"))
                .andExpect(view().name("all_users"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void delete_deletesEntityCorrectly() throws Exception {
        final UserServiceModel userServiceModel = registerUser("username", "random@abv.bg");
        mvc.perform(delete("/users/delete/" + userServiceModel.getId()))
                .andExpect(redirectedUrl("/users"));

        Assert.assertEquals(userRepository.count(), 1);
    }

//    @Test
//    @WithMockUser(username = "username")
////    TODO: TVA NE RABOTI
//    public void editTest() throws Exception {
//        final UserServiceModel userServiceModel = registerUser("username", "random@abv.bg");
//
//        this.mvc.perform(
//                put("/users/edit/" + userServiceModel.getId())
////                        .param("id", userServiceModel.getId())
//                        .param("name", "name_edit")
//                        .param("mail", "mailedit@abv.bg"));
//
//        User userActual = this.userRepository.findByUsername(userServiceModel.getUsername()).get();
//        Assert.assertEquals("name_edit", userActual.getName());
//    }

    private UserServiceModel registerUser(String username, String email) {
        final UserRegisterBindingModel userRegisterBindingModel = new UserRegisterBindingModel();
        userRegisterBindingModel.setUsername(username);
        userRegisterBindingModel.setConfirmPassword("123");
        userRegisterBindingModel.setPassword("123");
        userRegisterBindingModel.setMail(email);

        return userService.register(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
    }

    @Test
    @WithMockUser(username = "username")
    public void settings_returnsCorrectView() throws Exception {
        registerUser("username", "random@abv.bg");

        mvc.perform(get("/users/profile/settings"))
                .andExpect(view().name("user_settings"));
    }

//    @Test
//    @WithMockUser(username = "username")
////    TODO: NE RABOTI
//    public void testSettingsConfirm() throws Exception {
//        mvc.perform(put("users/profile/settings/" + registerUser("username", "random@abv.bg").getId())
//                .param("name", "new_name")
//                .param("mail", "newmail@abv.bg"));
//        final User user = userRepository.findByUsername("username").get();
//        Assert.assertEquals(user.getName(), "new_name");
////                .andExpect(redirectedUrl("users/profile/settings/"));
//    }


}
