package com.lulchev.busticketreservation.validation;

import com.lulchev.busticketreservation.commons.exceptions.MissingUserException;
import com.lulchev.busticketreservation.domain.entitiy.User;
import com.lulchev.busticketreservation.domain.model.binding.UserRegisterBindingModel;
import com.lulchev.busticketreservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@Validator
public class UserRegisterValidator implements org.springframework.validation.Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserRegisterValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterBindingModel.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRegisterBindingModel userRegisterBindingModel = (UserRegisterBindingModel) o;
        final User user = this.userRepository.findByUsername(userRegisterBindingModel.getUsername()).orElse(null);

        if (!passwordsMatch(userRegisterBindingModel.getPassword(), userRegisterBindingModel.getConfirmPassword())) {
            errors.rejectValue("password", "Passwords don't match", "Passwords don't match");
        }

        if (user != null) {
            errors.rejectValue("username", "Username is taken", "Username is taken");
        }
    }

    private boolean passwordsMatch(String password, String otherPassword) {
        return password != null && password.equals(otherPassword);
    }
}
