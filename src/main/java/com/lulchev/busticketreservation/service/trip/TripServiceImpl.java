package com.lulchev.busticketreservation.service.trip;

import com.lulchev.busticketreservation.commons.exceptions.MissingTripException;
import com.lulchev.busticketreservation.domain.entitiy.Trip;
import com.lulchev.busticketreservation.domain.model.service.TripServiceModel;
import com.lulchev.busticketreservation.repository.TripRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, ModelMapper modelMapper) {
        this.tripRepository = tripRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TripServiceModel> findAll() {
        return tripRepository.findAll().stream()
                .map(trip -> modelMapper.map(trip, TripServiceModel.class))
                .collect(toList());
    }

    @Override
    public void create(TripServiceModel tripServiceModel) {
        tripRepository.saveAndFlush(modelMapper.map(tripServiceModel, Trip.class));
    }

    @Override
    public void edit(String id, TripServiceModel tripServiceModel) {
        final Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new MissingTripException("Unable to find trip by id"));
        tripRepository.saveAndFlush(modelMapper.map(tripServiceModel, Trip.class));
    }

    @Override
    public void delete(String id) {
        tripRepository.deleteById(id);
    }

    @Override
    public TripServiceModel findById(String id) {
        return modelMapper.map(tripRepository.findById(id), TripServiceModel.class);
    }
}
