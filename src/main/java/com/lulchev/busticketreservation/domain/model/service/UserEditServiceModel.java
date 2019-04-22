package com.lulchev.busticketreservation.domain.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UserEditServiceModel extends BaseServiceModel {
    private String username;
    private String name;
    private String mail;
    private String password;
    private MultipartFile image;
}
