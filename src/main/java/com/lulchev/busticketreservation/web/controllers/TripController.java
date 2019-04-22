package com.lulchev.busticketreservation.web.controllers;

import com.lulchev.busticketreservation.domain.model.binding.TripAddBindingModel;
import com.lulchev.busticketreservation.domain.model.binding.TripEditBindingModel;
import com.lulchev.busticketreservation.domain.model.service.BusServiceModel;
import com.lulchev.busticketreservation.domain.model.service.LineServiceModel;
import com.lulchev.busticketreservation.domain.model.service.TripServiceModel;
import com.lulchev.busticketreservation.domain.model.view.BusListViewModel;
import com.lulchev.busticketreservation.domain.model.view.LineListViewModel;
import com.lulchev.busticketreservation.domain.model.view.TripListViewModel;
import com.lulchev.busticketreservation.service.bus.BusService;
import com.lulchev.busticketreservation.service.line.LineService;
import com.lulchev.busticketreservation.service.trip.TripService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/trips")
public class TripController extends BaseController {
    private final ModelMapper modelMapper;
    private final TripService tripService;
    private final LineService lineService;
    private final BusService busService;

    @Autowired
    public TripController(ModelMapper modelMapper, TripService tripService, LineService lineService, BusService busService) {
        this.modelMapper = modelMapper;
        this.tripService = tripService;
        this.lineService = lineService;
        this.busService = busService;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView create(ModelAndView modelAndView,
                               @ModelAttribute(name = "tripAddBindingModel") TripAddBindingModel tripAddBindingModel) {
        modelAndView.addObject("tripAddBindingModel", tripAddBindingModel);
        List<LineListViewModel> lines = lineService.findAll().stream().map(
                line -> modelMapper.map(line, LineListViewModel.class)
        ).collect(toList());

        List<BusListViewModel> buses = busService.findAll().stream().map(
                bus -> modelMapper.map(bus, BusListViewModel.class)
        ).collect(toList());

        modelAndView.addObject("lines", lines);
        modelAndView.addObject("buses", buses);
        return view("create_trip", modelAndView);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView createConfirm(
            @ModelAttribute(name = "tripAddBindingModel") TripAddBindingModel tripAddBindingModel) {
        TripServiceModel tripServiceModel = modelMapper.map(tripAddBindingModel, TripServiceModel.class);

        LineServiceModel lineServiceModel = lineService.findById(tripAddBindingModel.getLine());
        BusServiceModel busServiceModel = busService.findById(tripAddBindingModel.getBus());

        tripServiceModel.setLine(lineServiceModel);
        tripServiceModel.setBus(busServiceModel);
        tripServiceModel.setSeatsAvailable(busServiceModel.getCapacity());
        tripServiceModel.setSeatsReserved(0);
        tripService.create(tripServiceModel);
        return redirect("/trips");
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView fetchAll(ModelAndView modelAndView) {
        List<TripListViewModel> tripListViewModels = tripService.findAll().stream()
                .map(trip -> modelMapper.map(trip, TripListViewModel.class)).collect(toList());
        modelAndView.addObject("tripListViewModels", tripListViewModels);

        return view("all_trips", modelAndView);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView edit(@PathVariable(name = "id") String id,
                             @ModelAttribute(name = "tripEditBindingModel") TripEditBindingModel tripEditBindingModel) {
        TripServiceModel tripServiceModel = modelMapper.map(tripEditBindingModel, TripServiceModel.class);

        LineServiceModel lineServiceModel = lineService.findById(tripEditBindingModel.getLine());
        BusServiceModel busServiceModel = busService.findById(tripEditBindingModel.getBus());

        tripServiceModel.setLine(lineServiceModel);
        tripServiceModel.setBus(busServiceModel);
        tripService.edit(id, tripServiceModel);
        return redirect("/trips");
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView delete(@PathVariable(name = "id") String id) {
        tripService.delete(id);
        return redirect("/trips");
    }
}
