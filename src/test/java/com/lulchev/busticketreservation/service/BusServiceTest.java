package com.lulchev.busticketreservation.service;

import com.lulchev.busticketreservation.domain.entitiy.Bus;
import com.lulchev.busticketreservation.domain.model.service.BusServiceModel;
import com.lulchev.busticketreservation.repository.BusRepository;
import com.lulchev.busticketreservation.service.bus.BusServiceImpl;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BusServiceTest {
    @InjectMocks
    private BusServiceImpl busServiceImpl;
    @Mock
    private BusRepository busRepositoryMock;
    @Mock
    private ModelMapper modelMapperMock;
    @Mock
    private BusServiceModel busServiceModelMock;
    @Mock
    private Bus busMock;

    @Test
    public void findAllTest() {
        when(modelMapperMock.map(busServiceModelMock, Bus.class)).thenReturn(busMock);
        when(modelMapperMock.map(busMock, BusServiceModel.class)).thenReturn(busServiceModelMock);
        when(busRepositoryMock.findAll()).thenReturn(Collections.singletonList(busMock));

        busServiceImpl.findAll();

        verify(busRepositoryMock).findAll();
        verify(modelMapperMock).map(busMock, BusServiceModel.class);
    }

    @Test
    public void createbusTest() {
        when(modelMapperMock.map(busServiceModelMock, Bus.class)).thenReturn(busMock);

        busServiceImpl.create(busServiceModelMock);

        verify(busRepositoryMock).saveAndFlush(busMock);
        verify(modelMapperMock).map(busServiceModelMock, Bus.class);
    }

    @Test
    public void deletebusTest() {
        when(busRepositoryMock.findById("id")).thenReturn(Optional.of(busMock));

        busServiceImpl.delete("id");

        verify(busRepositoryMock).deleteById("id");
    }

    @Test
    public void editbusTest() {
        when(modelMapperMock.map(busServiceModelMock, Bus.class)).thenReturn(busMock);
        when(busRepositoryMock.findById("id")).thenReturn(Optional.of(busMock));

        busServiceImpl.edit("id", busServiceModelMock);

        verify(busRepositoryMock).findById("id");
        verify(busRepositoryMock).saveAndFlush(busMock);
    }

    @Test
    public void findByIdbusTest() {
        when(busRepositoryMock.findById("id")).thenReturn(Optional.of(busMock));
        when(modelMapperMock.map(busMock, BusServiceModel.class)).thenReturn(busServiceModelMock);
        Assert.assertEquals(busServiceImpl.findById("id"), busServiceModelMock);
    }
}
