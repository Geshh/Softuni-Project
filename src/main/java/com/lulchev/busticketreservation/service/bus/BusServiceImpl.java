package com.lulchev.busticketreservation.service.bus;

import com.lulchev.busticketreservation.commons.exceptions.MissingBusException;
import com.lulchev.busticketreservation.domain.entitiy.Bus;
import com.lulchev.busticketreservation.domain.model.service.BusServiceModel;
import com.lulchev.busticketreservation.repository.BusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class BusServiceImpl implements BusService {
    private final BusRepository busRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BusServiceImpl(BusRepository busRepository, ModelMapper modelMapper) {
        this.busRepository = busRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BusServiceModel> findAll() {
        return busRepository.findAll().stream()
                .map(bus -> modelMapper.map(bus, BusServiceModel.class))
                .collect(toList());
    }

    @Override
    public void create(BusServiceModel busServiceModel) {
        busRepository.saveAndFlush(modelMapper.map(busServiceModel, Bus.class));
    }

    @Override
    public void edit(String id, BusServiceModel busServiceModel) {
        final Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new MissingBusException("Unable to find bus by id"));
        busRepository.saveAndFlush(modelMapper.map(busServiceModel, Bus.class));
    }

    @Override
    public void delete(String id) {
        busRepository.deleteById(id);
    }

    @Override
    public BusServiceModel findById(String id) {
        Optional<Bus> optional = busRepository.findById(id);

        return optional.map(bus -> modelMapper.map(bus, BusServiceModel.class)).orElse(null);
    }
}
