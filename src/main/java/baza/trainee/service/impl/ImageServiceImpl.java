package baza.trainee.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

import baza.trainee.utils.CustomMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import baza.trainee.domain.model.Image;
import baza.trainee.repository.ImageRepository;
import baza.trainee.service.ImageService;
import baza.trainee.service.StorageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final StorageService storageService;
    private final ImageRepository imageRepository;

    @Override
    public byte[] loadAsResource(final String filename, final String quality) {
        return imageRepository.findById(filename)
                .map(Image::getData)
                .orElseGet(() -> storageService.loadAsResource(filename, quality));
    }

    private Function<? super MultipartFile, byte[]> mapFileToByteArrayFunction() {
        return f -> {
            try {
                return f.getResource().getContentAsByteArray();
            } catch (IOException e) {
                return new byte[0];
            }
        };
    }

    @Override
    public String storeToCache(final MultipartFile file) {
        try {
            var savedImage = imageRepository.save(Image.builder()
                    .name(file.getName())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .data(file.getBytes())
                    .build());
            return savedImage.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void persist(final String filename) {
        imageRepository.findById(filename)
                .ifPresent(storeFromCacheConsumer());

    }

    private Consumer<Image> storeFromCacheConsumer() {
        return im -> {
            try {
                var multipart = new CustomMultipartFile(
                        im.getName(),
                        im.getName(),
                        im.getContentType(),
                        new ByteArrayInputStream(im.getData()));

                storageService.store(multipart);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
