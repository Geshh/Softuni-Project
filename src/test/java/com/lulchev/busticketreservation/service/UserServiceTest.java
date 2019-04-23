package com.lulchev.busticketreservation.service;

import com.lulchev.busticketreservation.domain.entitiy.User;
import com.lulchev.busticketreservation.domain.model.service.RoleServiceModel;
import com.lulchev.busticketreservation.domain.model.service.UserEditServiceModel;
import com.lulchev.busticketreservation.domain.model.service.UserServiceModel;
import com.lulchev.busticketreservation.repository.UserRepository;
import com.lulchev.busticketreservation.service.role.RoleService;
import com.lulchev.busticketreservation.service.user.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private ModelMapper modelMapperMock;
    @Mock
    private UserServiceModel userServiceModelMock;
    @Mock
    private UserEditServiceModel userEditServiceModelMock;
    @Mock
    private User userMock;
    @Mock
    private RoleService roleServiceMock;
    @Mock
    private RoleServiceModel roleServiceModelMock;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoderMock;

    @Test
    public void findAllTest() {
        when(modelMapperMock.map(userServiceModelMock, User.class)).thenReturn(userMock);
        when(modelMapperMock.map(userMock, UserServiceModel.class)).thenReturn(userServiceModelMock);
        when(userRepositoryMock.findAll()).thenReturn(Collections.singletonList(userMock));

        userServiceImpl.findAll();

        verify(userRepositoryMock).findAll();
        verify(modelMapperMock).map(userMock, UserServiceModel.class);
    }

    @Test
    public void deleteUserTest() {
        when(userRepositoryMock.findById("id")).thenReturn(Optional.of(userMock));

        userServiceImpl.delete("id");

        verify(userRepositoryMock).deleteById("id");
    }

    @Test
    public void editUserTest() throws IOException {
        when(modelMapperMock.map(userServiceModelMock, User.class)).thenReturn(userMock);
        when(userRepositoryMock.findById("id")).thenReturn(Optional.of(userMock));

        userServiceImpl.edit("id", userEditServiceModelMock);

        verify(userRepositoryMock).findById("id");
        verify(userRepositoryMock).saveAndFlush(userMock);
    }

    @Test
    public void registerRootUserTest() {
        when(userRepositoryMock.count()).thenReturn(0L);
        when(modelMapperMock.map(userServiceModelMock, User.class)).thenReturn(userMock);
        when(modelMapperMock.map(userMock, UserServiceModel.class)).thenReturn(userServiceModelMock);
        Set<RoleServiceModel> roleServiceModels = new HashSet<>();
        roleServiceModels.add(roleServiceModelMock);
        when(roleServiceMock.findAllRoles()).thenReturn(roleServiceModels);
        when(userRepositoryMock.saveAndFlush(userMock)).thenReturn(userMock);

        userServiceImpl.register(userServiceModelMock);

        verify(userServiceModelMock).setAuthorities(roleServiceModels);
        verify(userRepositoryMock).saveAndFlush(userMock);
        verify(modelMapperMock).map(userServiceModelMock, User.class);
        verify(modelMapperMock).map(userMock, UserServiceModel.class);
    }

    @Test
    public void registerRegularUserTest() {
        when(userRepositoryMock.count()).thenReturn(1L);
        when(modelMapperMock.map(userServiceModelMock, User.class)).thenReturn(userMock);
        when(modelMapperMock.map(userMock, UserServiceModel.class)).thenReturn(userServiceModelMock);
        when(userRepositoryMock.saveAndFlush(userMock)).thenReturn(userMock);

        userServiceImpl.register(userServiceModelMock);

        verify(modelMapperMock).map(userServiceModelMock, User.class);
        verify(userRepositoryMock).saveAndFlush(userMock);
        verify(modelMapperMock).map(userMock, UserServiceModel.class);
    }

    @Test
    public void loadUserByUsernameTest() {
        when(userRepositoryMock.findByUsername("username")).thenReturn(Optional.of(userMock));

        userServiceImpl.loadUserByUsername("username");

        verify(userRepositoryMock).findByUsername("username");
    }

}
