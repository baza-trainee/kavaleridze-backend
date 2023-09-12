package baza.trainee.service.impl;

import baza.trainee.exceptions.custom.StorageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class FileSystemStorageService {

    private FileSystemStorageService() {
        throw new IllegalStateException("Utility class.");
    }

    /**
     * Load file path by filename in provided directory.
     *
     * @param filename name of the file.
     * @param location root directory.
     * @return {@link Path} of the file.
     */
    public static Path loadPath(final String filename, final Path location) {
        Path filePath = Paths.get(filename);
        try (Stream<Path> pathStream = Files.walk(location)) {
            return pathStream
                    .filter(path -> path.equals(filePath))
                    .findFirst()
                    .orElseThrow();
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files");
        }
    }

    /**
     * Store {@link MultipartFile} to given directory.
     *
     * @param file {@link MultipartFile} file.
     * @param location {@link Path} to store.
     */
    public static void storeToPath(final MultipartFile file, final Path location) {
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
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.");
        }
    }

    /**
     * Create directories by provided paths.
     *
     * @param paths iterable of {@link Path}
     */
    public static void init(final Path... paths) {
        try {
            for (Path path : paths) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage");
        }
    }
}
