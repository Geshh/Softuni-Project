package com.lulchev.busticketreservation.web.schedule;

import com.lulchev.busticketreservation.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
public class CronJobs {

    private final TripRepository tripRepository;

    @Autowired
    public CronJobs(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Scheduled(cron = "0 0 10 * * *")
    @Transactional
    public void removeDeparturedBuses() {
        tripRepository.deleteAllByDateTimeOfDepartureIsBefore(LocalDate.now());
    }
}
