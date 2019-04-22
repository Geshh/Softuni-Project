package com.lulchev.busticketreservation.service;

import com.lulchev.busticketreservation.domain.entitiy.ReservationTicket;
import com.lulchev.busticketreservation.domain.entitiy.Trip;
import com.lulchev.busticketreservation.domain.entitiy.User;
import com.lulchev.busticketreservation.domain.model.service.ReservationTicketServiceModel;
import com.lulchev.busticketreservation.repository.ReservationTicketRepository;
import com.lulchev.busticketreservation.repository.TripRepository;
import com.lulchev.busticketreservation.repository.UserRepository;
import com.lulchev.busticketreservation.service.reservation_ticket.ReservationTicketServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReservationTicketServiceTest {
    @InjectMocks
    private ReservationTicketServiceImpl reservationTicketServiceImpl;
    @Mock
    private ReservationTicketRepository reservationTicketRepositoryMock;
    @Mock
    private ModelMapper modelMapperMock;
    @Mock
    private ReservationTicketServiceModel reservationTicketServiceModelMock;
    @Mock
    private ReservationTicket reservationTicketMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private User userMock;
    @Mock
    private TripRepository tripRepositoryMock;
    @Mock
    private Trip tripMock;
    ;

    @Test
    public void findAllTest() {
        when(modelMapperMock.map(reservationTicketServiceModelMock, ReservationTicket.class)).thenReturn(reservationTicketMock);
        when(modelMapperMock.map(reservationTicketMock, ReservationTicketServiceModel.class)).thenReturn(reservationTicketServiceModelMock);
        when(reservationTicketRepositoryMock.findAll()).thenReturn(Collections.singletonList(reservationTicketMock));

        reservationTicketServiceImpl.findAll();

        verify(reservationTicketRepositoryMock).findAll();
        verify(modelMapperMock).map(reservationTicketMock, ReservationTicketServiceModel.class);
    }

    @Test
    public void deleteLineTest() {
        when(reservationTicketRepositoryMock.findById("id")).thenReturn(Optional.of(reservationTicketMock));

        reservationTicketServiceImpl.delete("id");

        verify(reservationTicketRepositoryMock).deleteById("id");
    }

    @Test
    public void editLineTest() {
        when(modelMapperMock.map(reservationTicketServiceModelMock, ReservationTicket.class)).thenReturn(reservationTicketMock);
        when(reservationTicketRepositoryMock.findById("id")).thenReturn(Optional.of(reservationTicketMock));

        reservationTicketServiceImpl.edit("id", reservationTicketServiceModelMock);

        verify(reservationTicketRepositoryMock).findById("id");
        verify(reservationTicketRepositoryMock).saveAndFlush(reservationTicketMock);
    }

    @Test
    public void createTicketForUserTest() {
        when(tripRepositoryMock.findById("id")).thenReturn(Optional.of(tripMock));
        when(userRepositoryMock.findByUsername("username")).thenReturn(Optional.of(userMock));

        when(modelMapperMock.map(reservationTicketServiceModelMock, ReservationTicket.class)).thenReturn(reservationTicketMock);

        reservationTicketServiceImpl.createTicketForUser("id", "username");

        verify(tripRepositoryMock).saveAndFlush(tripMock);
        verify(reservationTicketRepositoryMock).saveAndFlush(any());
    }

    @Test
    public void findAllForUserByUsernameTest() {
        when(userRepositoryMock.findByUsername("username")).thenReturn(Optional.of(userMock));
        when(reservationTicketRepositoryMock.findAllByUser(userMock)).thenReturn(List.of(reservationTicketMock));

        reservationTicketServiceImpl.findAllForUserByUsername("username");

        verify(reservationTicketRepositoryMock).findAllByUser(any());
    }
}
