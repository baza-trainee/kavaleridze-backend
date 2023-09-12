package baza.trainee.service.impl;

import baza.trainee.exceptions.custom.StorageException;
import baza.trainee.exceptions.custom.StorageFileNotFoundException;
import baza.trainee.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${resources.files.images.root}")
    private String rootImagesLocation = "/images/original";

    @Value("${resources.files.images.original}")
    private String originalImagesLocation = "/images/original";

    @Value("${resources.files.images.compressed}")
    private String compressedImagesLocation = "/images/compressed";

    private final Path originalLocation;
    private final Path previewLocation;

    /**
     * FileSystemStorageService constructor.
     */
    public FileSystemStorageService() {
        Path rootPath = Paths.get(rootImagesLocation);
        this.originalLocation = Paths.get(originalImagesLocation);
        this.previewLocation = Paths.get(compressedImagesLocation);
    }

    @Override
    public void store(final MultipartFile file) {
        storeToPath(file, originalLocation);
        storeToPath(file, previewLocation);
    }

    private String storeToPath(final MultipartFile file, final Path location) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            String name = file.getName();
            Path destinationFile = location
                    .resolve(Paths.get(name))
                    .normalize()
                    .toAbsolutePath();
            if (!destinationFile.getParent().equals(location.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream,
                        destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);

                return destinationFile.toString();
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.");
        }
    }

    @Override
    public Path load(final String filename, final String quality) {
        if (quality.equals("preview")) {
            return loadPath(filename, previewLocation);
        } else {
            return loadPath(filename, originalLocation);
        }
    }

    private Path loadPath(final String filename, final Path location) {
        try (Stream<Path> pathStream = Files.walk(location)) {
            return pathStream
                    .filter(path -> path.endsWith(filename))
                    .findFirst()
                    .orElseThrow();
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files");
        }
    }

    @Override
    public byte[] loadAsResource(final String filename, final String quality) {
        try {
            var file = load(filename, quality);
            var resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource.getContentAsByteArray();
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        } catch (IOException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(originalLocation);
            Files.createDirectories(previewLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage");
        }
    }
}
