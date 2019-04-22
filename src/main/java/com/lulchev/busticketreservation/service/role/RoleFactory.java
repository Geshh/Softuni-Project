package com.lulchev.busticketreservation.service.role;

import com.lulchev.busticketreservation.domain.entitiy.Role;

public class RoleFactory {
    public Role createRole(String authority) {
        return new Role(authority);
    }
}