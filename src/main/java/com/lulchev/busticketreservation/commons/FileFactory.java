package com.lulchev.busticketreservation.commons;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class FileFactory {
    public File createTempFile(String fileName) throws IOException {
        return File.createTempFile("tmp", fileName);
    }
}