package com.lulchev.busticketreservation.domain.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserPasswordChangeBindingModel {
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @Size(min = 3, max = 10, message = "Password must be between 3 and 10 chars")
    private String password;
    @NotNull
    @Size(min = 3, max = 10, message = "Password must be between 3 and 10 chars")
    private String newPassword;
    @NotNull
    @Size(min = 3, max = 10, message = "Password must be between 3 and 10 chars")
    private String confirmNewPassword;
}
