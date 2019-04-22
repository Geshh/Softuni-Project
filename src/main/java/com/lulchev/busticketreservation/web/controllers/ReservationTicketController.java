package com.lulchev.busticketreservation.web.controllers;

import com.lulchev.busticketreservation.domain.model.view.ReservationTicketListViewModel;
import com.lulchev.busticketreservation.domain.model.view.TripListViewModel;
import com.lulchev.busticketreservation.service.reservation_ticket.ReservationTicketService;
import com.lulchev.busticketreservation.service.trip.TripService;
import com.lulchev.busticketreservation.service.user.UserService;
import com.lulchev.busticketreservation.web.annotations.Title;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/tickets")
public class ReservationTicketController extends BaseController {
    private final ModelMapper modelMapper;
    private final ReservationTicketService reservationTicketService;
    private final TripService tripService;

    @Autowired
    public ReservationTicketController(ModelMapper modelMapper, ReservationTicketService reservationTicketService, TripService tripService, UserService userService) {
        this.modelMapper = modelMapper;
        this.reservationTicketService = reservationTicketService;
        this.tripService = tripService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Title("All tickets")
    public ModelAndView fetchAll(ModelAndView modelAndView) {
        final List<ReservationTicketListViewModel> reservationTicketListViewModels = reservationTicketService.findAll().stream()
                .map(ticket -> modelMapper.map(ticket, ReservationTicketListViewModel.class))
                .collect(toList());
        modelAndView.addObject("reservationTicketListViewModels", reservationTicketListViewModels);

        return view("all_tickets", modelAndView);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView delete(@PathVariable(name = "id") String id) {
        reservationTicketService.delete(id);
        return redirect("/tickets");
    }


    @GetMapping("/buy")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView buyTicket(ModelAndView modelAndView) {
        List<TripListViewModel> tripListViewModels = tripService.findAll().stream()
                .map(trip -> modelMapper.map(trip, TripListViewModel.class)).collect(toList());
        modelAndView.addObject("tripListViewModels", tripListViewModels);

        return view("buy_tickets", modelAndView);
    }

    @PutMapping("/buy/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView buyTicketConfirm(@PathVariable(name = "id") String id, Principal principal) {
        reservationTicketService.createTicketForUser(id, principal.getName());
        return redirect("/home");
    }

}
