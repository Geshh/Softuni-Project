package com.lulchev.busticketreservation.validation;

import com.lulchev.busticketreservation.commons.exceptions.MissingUserException;
import com.lulchev.busticketreservation.domain.entitiy.User;
import com.lulchev.busticketreservation.domain.model.binding.UserPasswordChangeBindingModel;
import com.lulchev.busticketreservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;

@Validator
public class UserPasswordValidator implements org.springframework.validation.Validator {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserPasswordValidator(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserPasswordChangeBindingModel.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserPasswordChangeBindingModel userPasswordChangeBindingModel = (UserPasswordChangeBindingModel) o;
        final User user = this.userRepository.findByUsername(userPasswordChangeBindingModel.getUsername()).orElseThrow(() -> new MissingUserException("Username not found"));

        if (!passwordsMatch(userPasswordChangeBindingModel.getNewPassword(), userPasswordChangeBindingModel.getConfirmNewPassword())) {
            errors.rejectValue("newPassword", "Passwords don't match", "Passwords don't match");
        }

        if (!bCryptPasswordEncoder.matches(userPasswordChangeBindingModel.getPassword(), user.getPassword())) {
            errors.rejectValue("password", "Incorrect password", "Incorrect Password");
        }
    }

    private boolean passwordsMatch(String password, String otherPassword) {
        return password != null && password.equals(otherPassword);
    }
}
