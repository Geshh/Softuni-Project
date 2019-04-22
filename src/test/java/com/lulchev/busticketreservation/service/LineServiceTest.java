package com.lulchev.busticketreservation.service;


import com.lulchev.busticketreservation.domain.entitiy.Line;
import com.lulchev.busticketreservation.domain.model.service.LineServiceModel;
import com.lulchev.busticketreservation.repository.LineRepository;
import com.lulchev.busticketreservation.service.line.LineServiceImpl;
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
public class LineServiceTest {
    @InjectMocks
    private LineServiceImpl lineServiceImpl;
    @Mock
    private LineRepository lineRepositoryMock;
    @Mock
    private ModelMapper modelMapperMock;
    @Mock
    private LineServiceModel lineServiceModelMock;
    @Mock
    private Line lineMock;

    @Test
    public void findAllTest() {
        when(modelMapperMock.map(lineServiceModelMock, Line.class)).thenReturn(lineMock);
        when(modelMapperMock.map(lineMock, LineServiceModel.class)).thenReturn(lineServiceModelMock);
        when(lineRepositoryMock.findAll()).thenReturn(Collections.singletonList(lineMock));

        lineServiceImpl.findAll();

        verify(lineRepositoryMock).findAll();
        verify(modelMapperMock).map(lineMock, LineServiceModel.class);
    }

    @Test
    public void createLineTest() {
        when(modelMapperMock.map(lineServiceModelMock, Line.class)).thenReturn(lineMock);

        lineServiceImpl.create(lineServiceModelMock);

        verify(lineRepositoryMock).saveAndFlush(lineMock);
        verify(modelMapperMock).map(lineServiceModelMock, Line.class);
    }

    @Test
    public void deleteLineTest() {
        when(lineRepositoryMock.findById("id")).thenReturn(Optional.of(lineMock));

        lineServiceImpl.delete("id");

        verify(lineRepositoryMock).deleteById("id");
    }

    @Test
    public void editLineTest() {
        when(modelMapperMock.map(lineServiceModelMock, Line.class)).thenReturn(lineMock);
        when(lineRepositoryMock.findById("id")).thenReturn(Optional.of(lineMock));

        lineServiceImpl.edit("id", lineServiceModelMock);

        verify(lineRepositoryMock).findById("id");
        verify(lineRepositoryMock).saveAndFlush(lineMock);
    }

    @Test
    public void findByIdLineTest() {
        when(lineRepositoryMock.findById("id")).thenReturn(Optional.of(lineMock));
        when(modelMapperMock.map(lineMock, LineServiceModel.class)).thenReturn(lineServiceModelMock);
        Assert.assertEquals(lineServiceImpl.findById("id"), lineServiceModelMock);
    }
}
