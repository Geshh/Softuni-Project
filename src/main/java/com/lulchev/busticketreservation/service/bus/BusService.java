package com.lulchev.busticketreservation.service.bus;

import com.lulchev.busticketreservation.domain.model.service.BusServiceModel;

import java.util.List;

public interface BusService {
    List<BusServiceModel> findAll();

    void create(BusServiceModel busServiceModel);

    void edit(String id, BusServiceModel busServiceModel);

    void delete(String id);

    BusServiceModel findById(String bus);
}
