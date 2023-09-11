package baza.trainee.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    byte[] loadAsResource(String filename);

    String storeToCache(MultipartFile file);

    Map<String, String> persist(Collection<String> filenames);

}
