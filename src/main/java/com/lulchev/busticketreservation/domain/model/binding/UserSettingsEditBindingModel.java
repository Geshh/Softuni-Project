package com.lulchev.busticketreservation.domain.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UserSettingsEditBindingModel extends UserEditBindingModel {
    private MultipartFile image;
}

