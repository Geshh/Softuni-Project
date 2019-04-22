package com.lulchev.busticketreservation.service.trip;

import com.lulchev.busticketreservation.domain.model.service.TripServiceModel;

import java.util.List;

public interface TripService {
    List<TripServiceModel> findAll();

    void create(TripServiceModel tripServiceModel);

    void edit(String id, TripServiceModel tripServiceModel);

    void delete(String id);

    TripServiceModel findById(String id);
}
