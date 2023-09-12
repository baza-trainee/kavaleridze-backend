package baza.trainee.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    byte[] loadAsResource(String filename, String quality);

    String storeToCache(MultipartFile file);

    void persist(String filename);

}
