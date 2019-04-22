package com.lulchev.busticketreservation.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.lulchev.busticketreservation.commons.FileFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;
    private final FileFactory fileFactory;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary, FileFactory fileFactory) {
        this.cloudinary = cloudinary;
        this.fileFactory = fileFactory;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        final File file = fileFactory.createTempFile(multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        return cloudinary.uploader().upload(file, new HashMap<>()).get("url").toString();
    }
}
