package baza.trainee.api.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import baza.trainee.api.AdminImagesApiDelegate;
import baza.trainee.dto.SaveImageResponse;
import baza.trainee.service.ImageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminImageApiDelegateImpl implements AdminImagesApiDelegate {

    private final ImageService imageService;
    private final Authentication authentication;

    @Override
    public ResponseEntity<SaveImageResponse> saveImage(MultipartFile file) {
        var username = authentication.getName();
        return new ResponseEntity<>(
                imageService.storeToTemp(file, username),
                HttpStatus.CREATED);
    }
    
}
