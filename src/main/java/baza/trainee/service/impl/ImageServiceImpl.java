package baza.trainee.service.impl;

import baza.trainee.config.StorageProperties;
import baza.trainee.exceptions.custom.StorageException;
import baza.trainee.exceptions.custom.StorageFileNotFoundException;
import baza.trainee.service.ImageService;
import baza.trainee.utils.CustomMultipartFile;
import baza.trainee.utils.FileSystemStorageUtils;
import baza.trainee.utils.ImageCompressor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private static final int TARGET_WIDTH = 680;
    private static final float QUALITY = 0.5F;

    private final Path rootLocation;
    private final Path originalLocation;
    private final Path previewLocation;
    private final Path tempLocation;

    /**
     * ImageServiceImpl constructor.
     *
     * @param storageProperties image storage configuration properties.
     */
    public ImageServiceImpl(final StorageProperties storageProperties) {
        this.rootLocation = Paths.get(storageProperties.getRootImageLocation());
        this.originalLocation = rootLocation.resolve(storageProperties.getOriginalImagesLocation()).normalize();
        this.previewLocation = rootLocation.resolve(storageProperties.getCompressedImagesLocation()).normalize();
        this.tempLocation = rootLocation.resolve(storageProperties.getTempImagesLocation()).normalize();

        FileSystemStorageUtils.init(rootLocation, originalLocation, previewLocation, tempLocation);
    }

    @Override
    public byte[] loadResource(final String filename, final String type) {
        var currentPath = type.equals("preview") ? previewLocation : originalLocation;
        return getResourceFromPath(filename, currentPath);
    }

    @Override
    public byte[] loadTempResource(final String filename, final String sessionId) {
        Path tempPath = tempLocation.resolve(sessionId).normalize();
        return getResourceFromPath(filename, tempPath);
    }

    @Override
    public String storeToTemp(final MultipartFile file, final String sessionId) {
        String name = UUID.randomUUID() + ".jpeg";
        Path sessionTempPath = this.tempLocation
                .resolve(Paths.get(sessionId))
                .normalize();

        try {
            var updatedFile = new CustomMultipartFile(
                    name,
                    name,
                    file.getContentType(),
                    file.getInputStream());
            FileSystemStorageUtils.storeToPath(updatedFile, sessionTempPath);

            return name;
        } catch (IOException e) {
            throw new StorageException("Failed to read MultipartFile data.");
        }
    }

    @Override
    public void persist(final List<String> filenames, final String sessionId) {
        Path tempPath = tempLocation.resolve(sessionId).normalize();
        var tempFilePaths = filenames.stream()
                .map((String filename) -> FileSystemStorageUtils.loadPath(filename, tempPath))
                .collect(Collectors.toList());
        try {
            for (Path fp : tempFilePaths) {
                var imageFile = new File(String.valueOf(fp));
                CustomMultipartFile inputFile = new CustomMultipartFile(
                        imageFile.getName(),
                        imageFile.getName(),
                        "image/jpeg",
                        new FileInputStream(imageFile));
                FileSystemStorageUtils.storeToPath(inputFile, originalLocation);

                var compressedFile = ImageCompressor.compress(
                        inputFile,
                        TARGET_WIDTH,
                        QUALITY);
                FileSystemStorageUtils.storeToPath(compressedFile, previewLocation);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files");
        }
        try {
            FileSystemUtils.deleteRecursively(tempPath);
        } catch (IOException e) {
            throw new StorageException("Could not delete session temp folder");
        }
    }

    private static byte[] getResourceFromPath(final String filename, final Path currentPath) {
        try {
            var file = FileSystemStorageUtils.loadPath(filename, currentPath);
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

}
