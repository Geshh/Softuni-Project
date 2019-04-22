package com.lulchev.busticketreservation.web.controllers;

import com.lulchev.busticketreservation.domain.model.binding.LineAddBindingModel;
import com.lulchev.busticketreservation.domain.model.binding.LineEditBindingModel;
import com.lulchev.busticketreservation.domain.model.service.LineServiceModel;
import com.lulchev.busticketreservation.domain.model.view.LineListViewModel;
import com.lulchev.busticketreservation.service.line.LineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/lines")
public class LinesController extends BaseController {
    private final ModelMapper modelMapper;
    private final LineService lineService;

    @Autowired
    public LinesController(ModelMapper modelMapper, LineService lineService) {
        this.modelMapper = modelMapper;
        this.lineService = lineService;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView create(ModelAndView modelAndView,
                               @ModelAttribute(name = "lineAddBindingModel") LineAddBindingModel lineAddBindingModel) {
        modelAndView.addObject("lineAddBindingModel", lineAddBindingModel);
        return view("create_line", modelAndView);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView createConfirm(
            @ModelAttribute(name = "lineAddBindingModel") LineAddBindingModel lineAddBindingModel) {
        lineService.create(modelMapper.map(lineAddBindingModel, LineServiceModel.class));
        return redirect("/lines");
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView fetchAll(ModelAndView modelAndView) {
        List<LineListViewModel> lineListViewModels = lineService.findAll().stream()
                .map(line -> modelMapper.map(line, LineListViewModel.class)).collect(toList());
        modelAndView.addObject("lineListViewModels", lineListViewModels);

        return view("all_lines", modelAndView);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView edit(@PathVariable(name = "id") String id,
                             @ModelAttribute(name = "lineEditBindingModel") LineEditBindingModel lineEditBindingModel) {
        lineService.edit(id, modelMapper.map(lineEditBindingModel, LineServiceModel.class));
        return redirect("/lines");
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView delete(@PathVariable(name = "id") String id) {
        lineService.delete(id);
        return redirect("/lines");
    }
}
