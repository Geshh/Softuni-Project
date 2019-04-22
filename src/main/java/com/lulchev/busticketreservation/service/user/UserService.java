package com.lulchev.busticketreservation.service.user;

import com.lulchev.busticketreservation.domain.model.service.UserEditServiceModel;
import com.lulchev.busticketreservation.domain.model.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.List;

public interface UserService extends UserDetailsService {
    UserServiceModel register(UserServiceModel userServiceModel);

    List<UserServiceModel> findAll();

    void edit(String id, UserEditServiceModel userEditServiceModel) throws IOException;

    void delete(String id);
}