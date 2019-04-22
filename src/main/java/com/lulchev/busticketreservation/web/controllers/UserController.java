package com.lulchev.busticketreservation.web.controllers;

import com.lulchev.busticketreservation.commons.exceptions.ForbiddenActionOnRootException;
import com.lulchev.busticketreservation.domain.model.binding.UserEditBindingModel;
import com.lulchev.busticketreservation.domain.model.binding.UserPasswordChangeBindingModel;
import com.lulchev.busticketreservation.domain.model.binding.UserRegisterBindingModel;
import com.lulchev.busticketreservation.domain.model.binding.UserSettingsEditBindingModel;
import com.lulchev.busticketreservation.domain.model.service.UserEditServiceModel;
import com.lulchev.busticketreservation.domain.model.service.UserServiceModel;
import com.lulchev.busticketreservation.domain.model.view.ReservationTicketListViewModel;
import com.lulchev.busticketreservation.domain.model.view.UserListViewModel;
import com.lulchev.busticketreservation.domain.model.view.UserSettingsViewModel;
import com.lulchev.busticketreservation.service.reservation_ticket.ReservationTicketService;
import com.lulchev.busticketreservation.service.user.UserService;
import com.lulchev.busticketreservation.validation.UserPasswordValidator;
import com.lulchev.busticketreservation.validation.UserRegisterValidator;
import com.lulchev.busticketreservation.web.annotations.Title;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ReservationTicketService reservationTicketService;
    private final UserPasswordValidator userPasswordValidator;
    private final UserRegisterValidator userRegisterValidator;

    @Autowired
    public UserController(ModelMapper modelMapper,
                          UserService userService,
                          ReservationTicketService reservationTicketService,
                          UserPasswordValidator userPasswordValidator,
                          UserRegisterValidator userRegisterValidator) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.reservationTicketService = reservationTicketService;
        this.userPasswordValidator = userPasswordValidator;
        this.userRegisterValidator = userRegisterValidator;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Title("All users")
    public ModelAndView fetchAll(ModelAndView modelAndView) {
        final List<UserListViewModel> userListViewModels = userService.findAll().stream()
                .map(user -> modelMapper.map(user, UserListViewModel.class)).collect(toList());
        modelAndView.addObject("userListViewModels", userListViewModels);

        return view("all_users", modelAndView);
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    @Title("Register")
    public ModelAndView register(ModelAndView modelAndView,
                                 @ModelAttribute(name = "userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel) {
        modelAndView.addObject("userRegisterBindingModel", userRegisterBindingModel);
        return view("register", modelAndView);
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(
            @ModelAttribute(name = "userRegisterBindingModel") @Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult) {
        this.userRegisterValidator.validate(userRegisterBindingModel, bindingResult);

        if (bindingResult.hasErrors()) {
            return view("register");
        }

        userService.register(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        return redirect("/users/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    @Title("Login")
    public ModelAndView login() {
        return view("login");
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView edit(@PathVariable(name = "id") String id,
                             @ModelAttribute(name = "userEditBindingModel") UserEditBindingModel userEditBindingModel)
            throws IOException {
        userService.edit(id, modelMapper.map(userEditBindingModel, UserEditServiceModel.class));

        return redirect("/users");
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView delete(@PathVariable(name = "id") String id) {
        userService.delete(id);

        return redirect("/users");
    }

    @GetMapping("/profile/settings")
    @PreAuthorize("isAuthenticated()")
    @Title("User settings")
    public ModelAndView settings(Principal principal, ModelAndView modelAndView) {
        modelAndView.addObject("userSettingsViewModel",
                modelMapper.map(userService.loadUserByUsername(principal.getName()), UserSettingsViewModel.class));

        return view("user_settings", modelAndView);
    }

    @PutMapping("/profile/settings/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView settingsConfirm(@PathVariable(name = "id") String id,
                                        @ModelAttribute(name = "userEditBindingModel") UserSettingsEditBindingModel userSettingsEditBindingModel)
            throws IOException {
        userService.edit(id, modelMapper.map(userSettingsEditBindingModel, UserEditServiceModel.class));

        return redirect("/users/profile/settings");
    }

    @GetMapping("/profile/settings/password")
    @PreAuthorize("isAuthenticated()")
    @Title("Password change")
    public ModelAndView changePassword(
            Principal principal,
            @ModelAttribute(name = "userPasswordChangeBindingModel") UserPasswordChangeBindingModel userPasswordChangeBindingModel,
            ModelAndView modelAndView) {
        userPasswordChangeBindingModel.setUsername(principal.getName());
        modelAndView.addObject("userPasswordChangeBindingModel", userPasswordChangeBindingModel);

        return view("user_settings_change_password", modelAndView);
    }

    @PutMapping("/profile/settings/password")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView changePasswordConfirm(
            @Valid @ModelAttribute(name = "userPasswordChangeBindingModel") UserPasswordChangeBindingModel userPasswordChangeBindingModel,
            BindingResult bindingResult) throws UsernameNotFoundException, IOException {
        this.userPasswordValidator.validate(userPasswordChangeBindingModel, bindingResult);
        if (bindingResult.hasErrors()) {
            return view("user_settings_change_password");
        }

        userPasswordChangeBindingModel.setPassword(userPasswordChangeBindingModel.getNewPassword());
        userService.edit(
                modelMapper.map(userService.loadUserByUsername(userPasswordChangeBindingModel.getUsername()), UserServiceModel.class).getId(),
                modelMapper.map(userPasswordChangeBindingModel, UserEditServiceModel.class));

        return redirect("/users/profile/settings/password");
    }

    @GetMapping("/profile/settings/tickets")
    @PreAuthorize("isAuthenticated()")
    @Title("Tickets Purchased")
    public ModelAndView fetchTicketsForUser(Principal principal, ModelAndView modelAndView) {
        List<ReservationTicketListViewModel> reservationTicketListViewModels =
                reservationTicketService.findAllForUserByUsername(principal.getName())
                        .stream().map(ticket -> modelMapper.map(ticket, ReservationTicketListViewModel.class))
                        .collect(toList());
        modelAndView.addObject("reservationTicketListViewModels", reservationTicketListViewModels);

        return view("user_settings_tickets_purchased", modelAndView);
    }

    @ExceptionHandler(ForbiddenActionOnRootException.class)
    public ModelAndView handleForbiddenActionOnRootException() {
        return view("/errors/forbidden_action_on_root_error_page");
    }
}