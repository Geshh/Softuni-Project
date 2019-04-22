package com.lulchev.busticketreservation.domain.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterBindingModel {
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be between 3 and 10 chars")
    private String password;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be between 3 and 10 chars")
    private String confirmPassword;
    @NotNull
    @NotEmpty
    @Email
    private String mail;
}
