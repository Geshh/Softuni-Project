package com.lulchev.busticketreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BusticketreservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusticketreservationApplication.class, args);
    }

}
