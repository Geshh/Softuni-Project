package com.lulchev.busticketreservation.domain.model.binding;


import com.lulchev.busticketreservation.domain.model.service.RoleServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserEditBindingModel extends BaseBindingModel {
    private String name;
    @NotEmpty
    @NotNull
    @Email
    private String mail;

//    private List<String> authorities;
}