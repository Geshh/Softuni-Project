package com.lulchev.busticketreservation.domain.model.service;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel extends BaseServiceModel {
    @NotEmpty
    @NotNull
    private String username;

    private String name;

    @NotEmpty
    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private Set<RoleServiceModel> authorities = new HashSet<>();

    @NotEmpty
    @NotNull
    @Email(message = "Not a valid email")
    private String mail;
}
