package com.lulchev.busticketreservation.domain.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UserSettingsViewModel extends BaseViewModel {
    private String username;
    private String name;
    private String mail;
    private MultipartFile image;
}
