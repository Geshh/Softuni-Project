package com.lulchev.busticketreservation.service.line;

import com.lulchev.busticketreservation.domain.model.service.LineServiceModel;

import java.util.List;

public interface LineService {

    List<LineServiceModel> findAll();

    void create(LineServiceModel lineServiceModel);

    void edit(String id, LineServiceModel lineServiceModel);

    void delete(String id);

    LineServiceModel findById(String id);
}
