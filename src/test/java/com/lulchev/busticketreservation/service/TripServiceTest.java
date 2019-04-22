package com.lulchev.busticketreservation.service;

import com.lulchev.busticketreservation.domain.entitiy.Trip;
import com.lulchev.busticketreservation.domain.model.service.TripServiceModel;
import com.lulchev.busticketreservation.repository.TripRepository;
import com.lulchev.busticketreservation.service.trip.TripServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TripServiceTest {
    @InjectMocks
    private TripServiceImpl tripServiceImpl;
    @Mock
    private TripRepository tripRepositoryMock;
    @Mock
    private ModelMapper modelMapperMock;
    @Mock
    private TripServiceModel tripServiceModelMock;
    @Mock
    private Trip tripMock;

    @Test
    public void findAllTest() {
        when(modelMapperMock.map(tripServiceModelMock, Trip.class))
                .thenReturn(tripMock);
        when(modelMapperMock.map(tripMock, TripServiceModel.class))
                .thenReturn(tripServiceModelMock);
        when(tripRepositoryMock.findAll())
                .thenReturn(Collections.singletonList(tripMock));

        tripServiceImpl.findAll();

        verify(tripRepositoryMock).findAll();
        verify(modelMapperMock).map(tripMock, TripServiceModel.class);
    }

    @Test
    public void createTripTest() {
        when(modelMapperMock.map(tripServiceModelMock, Trip.class)).thenReturn(tripMock);

        tripServiceImpl.create(tripServiceModelMock);

        verify(tripRepositoryMock).saveAndFlush(tripMock);
        verify(modelMapperMock).map(tripServiceModelMock, Trip.class);
    }

    @Test
    public void deleteTripTest() {
        when(tripRepositoryMock.findById("id")).thenReturn(Optional.of(tripMock));

        tripServiceImpl.delete("id");

        verify(tripRepositoryMock).deleteById("id");
    }

    @Test
    public void editTripTest() {
        when(modelMapperMock.map(tripServiceModelMock, Trip.class)).thenReturn(tripMock);
        when(tripRepositoryMock.findById("id")).thenReturn(Optional.of(tripMock));

        tripServiceImpl.edit("id", tripServiceModelMock);

        verify(tripRepositoryMock).findById("id");
        verify(tripRepositoryMock).saveAndFlush(tripMock);
    }

//    @Test
////    TODO:FIX
//    public void findByIdbusTest() {
//        when(tripRepositoryMock.findById("id")).thenReturn(Optional.of(tripMock));
//        when(modelMapperMock.map(tripMock, TripServiceModel.class)).thenReturn(tripServiceModelMock);
//        Assert.assertEquals(tripServiceImpl.findById("id"), tripServiceModelMock);
//    }
}
