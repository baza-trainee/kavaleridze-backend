package baza.trainee.api.impl;

import baza.trainee.api.ImagesApiDelegate;
import baza.trainee.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Controller class for managing image-related HTTP requests and responses.
 * This controller provides endpoints for retrieving images and saving temporary images.
 *
 * @author Evhen Malysh
 */
@Service
@RequiredArgsConstructor
public class ImageApiDelegateImpl implements ImagesApiDelegate {

    private final ImageService storageService;

    @Override
    public ResponseEntity<byte[]> getImage(String filename, String type) {
        return new ResponseEntity<>(storageService.loadResource(filename, type), HttpStatusCode.valueOf(200));
    }
}
