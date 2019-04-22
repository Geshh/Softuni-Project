package com.lulchev.busticketreservation.web.controllers;

import com.lulchev.busticketreservation.domain.model.binding.BusAddBindingModel;
import com.lulchev.busticketreservation.domain.model.binding.BusEditBindingModel;
import com.lulchev.busticketreservation.domain.model.service.BusServiceModel;
import com.lulchev.busticketreservation.domain.model.view.BusListViewModel;
import com.lulchev.busticketreservation.service.bus.BusService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/buses")
public class BusController extends BaseController {
    private final ModelMapper modelMapper;
    private final BusService busService;

    @Autowired
    public BusController(ModelMapper modelMapper, BusService busService) {
        this.modelMapper = modelMapper;
        this.busService = busService;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView create(ModelAndView modelAndView,
                               @ModelAttribute(name = "busAddBindingModel") BusAddBindingModel busAddBindingModel) {
        modelAndView.addObject("busAddBindingModel", busAddBindingModel);
        return view("create_bus", modelAndView);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView createConfirm(
            @ModelAttribute(name = "busAddBindingModel") BusAddBindingModel busAddBindingModel) {
        busService.create(modelMapper.map(busAddBindingModel, BusServiceModel.class));
        return redirect("/buses");
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView fetchAll(ModelAndView modelAndView) {
        List<BusListViewModel> busListViewModels = busService.findAll().stream()
                .map(bus -> modelMapper.map(bus, BusListViewModel.class)).collect(toList());
        modelAndView.addObject("busListViewModels", busListViewModels);

        return view("all_buses", modelAndView);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView edit(@PathVariable(name = "id") String id,
                             @ModelAttribute(name = "busEditBindingModel") BusEditBindingModel busEditBindingModel) {
        busService.edit(id, modelMapper.map(busEditBindingModel, BusServiceModel.class));
        return redirect("/buses");
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView delete(@PathVariable(name = "id") String id) {
        busService.delete(id);
        return redirect("/buses");
    }
}
