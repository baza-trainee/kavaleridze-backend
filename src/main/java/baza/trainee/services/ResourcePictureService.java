package baza.trainee.services;

import org.springframework.core.io.Resource;

public interface ResourcePictureService {
    Resource loadAsResource(String filename);
}
