package com.lulchev.busticketreservation.service;

import com.lulchev.busticketreservation.domain.entitiy.Role;
import com.lulchev.busticketreservation.domain.model.service.RoleServiceModel;
import com.lulchev.busticketreservation.repository.RoleRepository;
import com.lulchev.busticketreservation.service.role.RoleFactory;
import com.lulchev.busticketreservation.service.role.RoleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleServiceTest {
    @InjectMocks
    private RoleServiceImpl roleServiceImpl;
    @Mock
    private RoleRepository roleRepositoryMock;
    @Mock
    private ModelMapper modelMapperMock;
    @Mock
    private RoleFactory roleFactoryMock;
    @Mock
    private Role roleUserMock;
    @Mock
    private Role roleAdminMock;
    @Mock
    private Role roleRootMock;

    @Test
    public void testFindByAuthority_RoleUser_ReturnsCorrect() {
        when(roleRepositoryMock.findByAuthority("ROLE_USER")).thenReturn(roleUserMock);

        roleServiceImpl.findByAuthority("ROLE_USER");

        verify(roleRepositoryMock).findByAuthority("ROLE_USER");
        verify(modelMapperMock).map(roleUserMock, RoleServiceModel.class);
    }

    @Test
    public void testFindByAuthority_RoleAdmin_ReturnsCorrect() {
        when(roleRepositoryMock.findByAuthority("ROLE_ADMIN")).thenReturn(roleAdminMock);

        roleServiceImpl.findByAuthority("ROLE_ADMIN");

        verify(roleRepositoryMock).findByAuthority("ROLE_ADMIN");
        verify(modelMapperMock).map(roleAdminMock, RoleServiceModel.class);
    }

    @Test
    public void testFindByAuthority_RoleRoot_ReturnsCorrect() {
        when(roleRepositoryMock.findByAuthority("ROLE_ROOT")).thenReturn(roleRootMock);

        roleServiceImpl.findByAuthority("ROLE_ROOT");

        verify(roleRepositoryMock).findByAuthority("ROLE_ROOT");
        verify(modelMapperMock).map(roleRootMock, RoleServiceModel.class);
    }

    @Test
    public void testFindAllRoles() {
        when(roleRepositoryMock.findAll()).thenReturn(List.of(roleUserMock, roleAdminMock, roleRootMock));

        roleServiceImpl.findAllRoles();

        verify(roleRepositoryMock).findAll();
        verify(modelMapperMock).map(roleUserMock, RoleServiceModel.class);
        verify(modelMapperMock).map(roleRootMock, RoleServiceModel.class);
        verify(modelMapperMock).map(roleAdminMock, RoleServiceModel.class);
    }

    @Test
    public void testSeedDefaultRolesInDb_SeedsRoles_WhenNoRoles() {
        when(roleRepositoryMock.count()).thenReturn(0L);
        when(roleFactoryMock.createRole("ROLE_ADMIN")).thenReturn(roleAdminMock);
        when(roleFactoryMock.createRole("ROLE_USER")).thenReturn(roleUserMock);
        when(roleFactoryMock.createRole("ROLE_ROOT")).thenReturn(roleRootMock);

        roleServiceImpl.seedDefaultRolesInDb();

        verify(roleRepositoryMock).saveAndFlush(roleAdminMock);
        verify(roleRepositoryMock).saveAndFlush(roleUserMock);
        verify(roleRepositoryMock).saveAndFlush(roleRootMock);
    }

    @Test
    public void testSeedDefaultRolesInDb_DoesNotSeed_WhenRolesAreAvailable() {
        when(roleRepositoryMock.count()).thenReturn(3L);

        roleServiceImpl.seedDefaultRolesInDb();

        verify(roleRepositoryMock, never()).saveAndFlush(roleAdminMock);
        verify(roleRepositoryMock, never()).saveAndFlush(roleUserMock);
        verify(roleRepositoryMock, never()).saveAndFlush(roleRootMock);
    }
}
