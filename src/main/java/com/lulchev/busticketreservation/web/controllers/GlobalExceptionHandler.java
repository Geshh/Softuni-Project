package com.lulchev.busticketreservation.web.controllers;

import com.lulchev.busticketreservation.commons.exceptions.MissingBusException;
import com.lulchev.busticketreservation.commons.exceptions.MissingLineException;
import com.lulchev.busticketreservation.commons.exceptions.MissingTripException;
import com.lulchev.busticketreservation.commons.exceptions.MissingUserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    private static final String ERROR_PAGES_PATH = "/errors";

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleAllExceptions(Throwable throwable) {
        return view(ERROR_PAGES_PATH + "/generic_error_page");
    }

    @ExceptionHandler({MissingUserException.class, MissingBusException.class,
            MissingLineException.class, MissingTripException.class})
    public ModelAndView handleNotFoundExceptions(RuntimeException runtimeException) {
        return view(ERROR_PAGES_PATH + "/not_found_error_page");
    }

}
