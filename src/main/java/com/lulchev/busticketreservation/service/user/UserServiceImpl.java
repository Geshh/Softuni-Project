package com.lulchev.busticketreservation.service.user;


import com.lulchev.busticketreservation.commons.exceptions.ForbiddenActionOnRootException;
import com.lulchev.busticketreservation.commons.exceptions.MissingUserException;
import com.lulchev.busticketreservation.domain.entitiy.User;
import com.lulchev.busticketreservation.domain.model.service.UserEditServiceModel;
import com.lulchev.busticketreservation.domain.model.service.UserServiceModel;
import com.lulchev.busticketreservation.repository.UserRepository;
import com.lulchev.busticketreservation.service.cloudinary.CloudinaryService;
import com.lulchev.busticketreservation.service.role.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unable to find user by name"));
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        roleService.seedDefaultRolesInDb();

        if (userRepository.count() == 0) {
            userServiceModel.setAuthorities(roleService.findAllRoles());
        } else {
            userServiceModel.getAuthorities().add(roleService.findByAuthority("ROLE_USER"));
        }

        final User user = modelMapper.map(userServiceModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        return modelMapper.map(userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAll() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserServiceModel.class))
                .collect(toList());
    }

    @Override
    public void edit(String id, UserEditServiceModel userEditServiceModel) throws IOException {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new MissingUserException("Unable to find user by username"));
        forbidActionOnRoot(user);


        if (isNotBlank(userEditServiceModel.getUsername())) {
            user.setUsername(userEditServiceModel.getUsername());
        }
        if (isNotBlank(userEditServiceModel.getMail())) {
            user.setMail(userEditServiceModel.getMail());
        }
        if (isNotBlank(userEditServiceModel.getName())) {
            user.setName(userEditServiceModel.getName());
        }
        if (isNotBlank(userEditServiceModel.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userEditServiceModel.getPassword()));
        }
        if (isImageSelected(userEditServiceModel.getImage())) {
            user.setImage_path(cloudinaryService.uploadImage(userEditServiceModel.getImage()));
        }

        userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(String id) {
        forbidActionOnRoot(
                userRepository.findById(id).orElseThrow(() -> new MissingUserException("Unable to find user by id")));
        userRepository.deleteById(id);
    }

    private boolean isImageSelected(MultipartFile image) {
        return image != null && !image.isEmpty();
    }

    private void forbidActionOnRoot(final User user) {
        if (user.getAuthorities().stream().anyMatch(role -> role.getAuthority().equalsIgnoreCase("ROLE_ROOT"))) {
            throw new ForbiddenActionOnRootException("Cannot modify root");
        }
    }
}