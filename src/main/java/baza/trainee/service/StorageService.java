package baza.trainee.service;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void store(MultipartFile file);

    Path load(String filename, String quality);

    byte[] loadAsResource(String filename, String quality);

    void init();

}
