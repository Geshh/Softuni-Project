package com.lulchev.busticketreservation.service.role;

import com.lulchev.busticketreservation.domain.model.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {
    void seedDefaultRolesInDb();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
