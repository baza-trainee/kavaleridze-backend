package baza.trainee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import baza.trainee.service.ImageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService storageService;

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    byte[] getImage(
        @RequestParam("filename") final String filename,
        @RequestParam("quality") final String quality
    ) {
        return storageService.loadAsResource(filename, quality);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    String saveImage(@RequestParam("file") final MultipartFile file) {
        return storageService.storeToCache(file);
    }
}
