package com.lulchev.busticketreservation.domain.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserListViewModel extends BaseViewModel {

    private String username;
    private String name;
    private List<RoleViewModel> authorities;
    private String mail;
}
