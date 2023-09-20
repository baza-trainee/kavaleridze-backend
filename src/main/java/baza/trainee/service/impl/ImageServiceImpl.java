package baza.trainee.service.impl;

import static baza.trainee.service.impl.ImageServiceImpl.ImageType.DESKTOP;
import static baza.trainee.service.impl.ImageServiceImpl.ImageType.PREVIEW;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import baza.trainee.config.ImageCompressionConfig;
import baza.trainee.config.StorageProperties;
import baza.trainee.exceptions.custom.StorageException;
import baza.trainee.exceptions.custom.StorageFileNotFoundException;
import baza.trainee.service.ImageService;
import baza.trainee.utils.FileSystemStorageUtils;
import baza.trainee.utils.ImageCompressor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of the {@link ImageService} interface for managing
 * image-related operations.
 * This service handles loading, storing, and processing images.
 *
 * @author Evhen Malysh
 */
@Service
public class ImageServiceImpl implements ImageService {

    private final int previewWidth;
    private final float previewQuality;
    private final int desktopWidth;
    private final float desktopQuality;

    private final Path rootPath;
    private final Path tempPath;

    private final String originalDirName;
    private final String previewDirName;

    /**
     * Constructs an instance of the ImageServiceImpl class.
     *
     * @param storageProperties Image storage configuration properties.
     * @param compressionConfig Image compression properties.
     * @throws StorageException If root or temp directoire can`t be initialized.
     */
    public ImageServiceImpl(
            final StorageProperties storageProperties,
            final ImageCompressionConfig compressionConfig) {
        this.rootPath = Paths.get(storageProperties.getRootImageLocation());
        this.tempPath = rootPath.resolve(storageProperties.getTempImagesLocation()).normalize();

        this.originalDirName = storageProperties.getOriginalImagesLocation();
        this.previewDirName = storageProperties.getCompressedImagesLocation();

        this.previewWidth = compressionConfig.getPreviewWidth();
        this.previewQuality = compressionConfig.getPreviewQuality();
        this.desktopWidth = compressionConfig.getDesktopWidth();
        this.desktopQuality = compressionConfig.getDesktopQuality();

        FileSystemStorageUtils.init(rootPath, tempPath);
    }

    /**
     * Load a resource (image) by filename and type.
     *
     * @param fileId The name of the file.
     * @param type   The type of the resource (either "preview" or "original").
     * @return A byte array containing the resource data.
     * @throws StorageFileNotFoundException If the resource file cannot be found or
     *                                      read.
     */
    @Override
    public byte[] loadResource(final String fileId, final String type) {
        var currentPath = getCurrentPath(rootPath, fileId, type);

        return getResourceFromPath(fileId, currentPath);
    }

    /**
     * Load a temporary resource (image) by filename and session ID.
     *
     * @param fileId    The name of the file.
     * @param sessionId The ID of the session associated with the temporary
     *                  resource.
     * @return A byte array containing the temporary resource data.
     * @throws StorageFileNotFoundException If the resource file cannot be found or
     *                                      read.
     */
    @Override
    public byte[] loadTempResource(
            final String fileId,
            final String sessionId,
            final String type) {
        var currentPath = getCurrentPath(tempPath, fileId, type);

        return getResourceFromPath(fileId, currentPath);
    }

    /**
     * Convert and store a image from multipart file as a temporary resources.
     *
     * @param file      The image as {@link MultipartFile} to store.
     * @param sessionId The ID of the session associated with the temporary
     *                  resource.
     * @return The ID of the stored file that represent name of subfolder
     *         with converted images.
     * @throws StorageException If an error occurs while storing the file.
     */
    @Override
    public String storeToTemp(final MultipartFile file, final String sessionId) {
        var imageId = UUID.randomUUID().toString();
        var sessionTempPath = this.tempPath.resolve(sessionId);
        convertAndStore(sessionTempPath, file, DESKTOP, PREVIEW);

        return imageId;
    }

    /**
     * Persist and process a list of filenames associated with a session.
     *
     * @param imageIds  The list of filenames to persist and process.
     * @param sessionId The ID of the session associated with the files.
     * @throws StorageException If an error occurs while persisting or processing
     *                          the files.
     */
    @Override
    public void persist(final List<String> imageIds, final String sessionId) {
        var sessionPath = tempPath.resolve(sessionId);

        for (var imageId : imageIds) {
            var imageTempPath = FileSystemStorageUtils.loadPath(imageId, sessionPath);
            FileSystemStorageUtils.copyRecursively(imageTempPath, rootPath);
        }
        FileSystemStorageUtils.deleteRecursively(sessionPath);
    }

    private Path getCurrentPath(final Path root, final String fileId, final String type) {
        var imageType = ImageType.valueOf(type);

        var currentDir = switch (imageType) {
            case DESKTOP -> originalDirName;
            case PREVIEW -> previewDirName;
        };

        var currentPath = root
                .resolve(fileId)
                .resolve(currentDir);

        return currentPath;
    }

    private void convertAndStore(Path basePath, MultipartFile image, ImageType... types) {
        for (var imageType : types) {
            Path directPath = switch (imageType) {
                case DESKTOP -> basePath.resolve(originalDirName);
                case PREVIEW -> basePath.resolve(previewDirName);
            };
            MultipartFile compressedFile = switch (imageType) {
                case DESKTOP -> ImageCompressor.compress(
                        image,
                        desktopWidth,
                        desktopQuality);
                case PREVIEW -> ImageCompressor.compress(
                        image,
                        previewWidth,
                        previewQuality);
            };
            FileSystemStorageUtils.storeToPath(compressedFile, directPath);
        }
    }

    private byte[] getResourceFromPath(final String filename, final Path currentPath) {
        var file = FileSystemStorageUtils.loadPath(filename, currentPath);
        var resource = FileSystemStorageUtils.getResource(file.toUri());

        return FileSystemStorageUtils.getByteArrayFromResource(resource);
    }

    @Getter
    @RequiredArgsConstructor
    public enum ImageType {
        DESKTOP("original"),
        PREVIEW("preview");

        private final String value;
    }

}
