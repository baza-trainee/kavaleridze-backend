package baza.trainee.service.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import baza.trainee.domain.model.Image;
import baza.trainee.service.ImageService;
import baza.trainee.service.StorageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final StorageService storageService;
    private final RedisTemplate<String, Image> template;

    @Override
    public byte[] loadAsResource(final String filename) {
        return Optional.ofNullable(template.opsForValue().get(filename))
                .map(Image::getValue)
                .map(mapFileToByteArrayFunction())
                .orElseGet(() -> storageService.loadAsResource(filename));
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
        String fileName = file.getName();
        template.opsForValue().set(fileName, new Image(fileName, file));

        return fileName;
    }

    @Override
    public Map<String, String> persist(final Collection<String> filenames) {
        return Optional.ofNullable(template.opsForValue().multiGet(filenames))
                .map(storeFilesFromCacheFunction())
                .orElseGet(loadPathsSupplier(filenames));
    }

    private Function<List<Image>, Map<String, String>> storeFilesFromCacheFunction() {
        return ims -> ims.stream()
                .map(Image::getValue)
                .collect(Collectors.toMap(
                        MultipartFile::getOriginalFilename,
                        storageService::store));
    }

    private Supplier<Map<String, String>> loadPathsSupplier(final Collection<String> filenames) {
        return () -> filenames.stream()
                .collect(Collectors.toMap(
                        f -> f,
                        f -> storageService.load(f).toString()));
    }
}
