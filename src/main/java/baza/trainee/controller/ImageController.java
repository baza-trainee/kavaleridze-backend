package baza.trainee.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
            @RequestParam("type") final String type
    ) {
        return storageService.loadResource(filename, type);
    }

    @GetMapping(value = "/temp", produces = MediaType.IMAGE_JPEG_VALUE)
    byte[] getTempImage(
            final HttpSession session,
            @RequestParam("filename") final String filename
    ) {
        String sessionId = session.getId();
        return storageService.loadTempResource(filename, sessionId);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    String saveImage(
            final HttpSession session,
            @RequestParam("file") final MultipartFile file
    ) {
        String sessionId = session.getId();

        return storageService.storeToTemp(file, sessionId);
    }
}
