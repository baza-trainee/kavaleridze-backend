package baza.trainee.api.impl;

import baza.trainee.api.ImagesApiDelegate;
import baza.trainee.service.ImageService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageApiDelegateImpl implements ImagesApiDelegate {

    private final ImageService imageService;
    private final Authentication authentication;

    @Override
    public ResponseEntity<byte[]> getImage(String filename, String type) {
        return new ResponseEntity<>(imageService.loadResource(filename, type), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getTempImage(String filename, String type) {
        var username = authentication.getName();
        return new ResponseEntity<>(
                imageService.loadTempResource(filename, username, type),
                HttpStatus.OK);
    }
}
