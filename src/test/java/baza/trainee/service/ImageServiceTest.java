package baza.trainee.service;

import baza.trainee.config.ImageCompressionConfig;
import baza.trainee.config.StorageProperties;
import baza.trainee.service.impl.ImageServiceImpl;
import baza.trainee.service.impl.ImageServiceImpl.ImageType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImageServiceTest {

    private StorageProperties storageProperties = new StorageProperties();
    private ImageCompressionConfig imageProperties = new ImageCompressionConfig();

    @TempDir
    private Path rootImageLocation;
    private File testImage;

    private String desktopDir;
    private String previewDir;
    private String tempDir;

    private ImageService imageService;

    @BeforeEach
    void init() {
        storageProperties.setRootImageLocation(rootImageLocation.toString());
        desktopDir = storageProperties.getOriginalImagesLocation();
        previewDir = storageProperties.getCompressedImagesLocation();
        tempDir = storageProperties.getTempImagesLocation();

        imageService = new ImageServiceImpl(storageProperties, imageProperties);

        testImage = new File("src/test/resources/test-images/test.jpg");
    }

    @Test
    void initializationTest() {

        // given:
        Path tempPath = rootImageLocation.resolve(tempDir);

        File originalDirectory = new File(rootImageLocation.toUri());
        File tempDirectory = new File(tempPath.toUri());

        // expected:
        assertTrue(Files.isDirectory(rootImageLocation));
        assertTrue(Files.isDirectory(tempPath));
        assertTrue(originalDirectory.exists());
        assertTrue(tempDirectory.exists());
    }

    @Test
    void storeToTempTest() throws IOException {

        // given:
        var file = new MockMultipartFile(
                testImage.getName(),
                testImage.getName(),
                "image/jpeg",
                new FileInputStream(testImage));

        var sessionId = "fakeSessionId";

        var desktopDestDir = rootImageLocation
                .resolve(tempDir)
                .resolve(sessionId)
                .resolve(desktopDir);
        var previewDestDir = rootImageLocation
                .resolve(tempDir)
                .resolve(sessionId)
                .resolve(previewDir);

        // when:
        var generatedFileName = imageService.storeToTemp(file, sessionId);

        // then:
        assertFalse(generatedFileName.isBlank());

        // when:
        var createdDesktopFile = desktopDestDir.resolve(generatedFileName).toFile();
        var createdPreviewFile = previewDestDir.resolve(generatedFileName).toFile();

        // then:
        assertTrue(createdDesktopFile.exists());
        assertTrue(createdPreviewFile.exists());
    }

    @Test
    void persistTest() throws IOException {

        // given:
        String imageFileName = testImage.getName();

        var file = new MockMultipartFile(imageFileName, new FileInputStream(testImage));
        var sessionId = "fakeSessionId";

        var generatedFileId = imageService.storeToTemp(file, sessionId);

        // Path => /root/image-UUID/desktop/file.webp
        var createdOriginalFile = rootImageLocation
                .resolve(generatedFileId)
                .resolve(desktopDir)
                .resolve(imageFileName.replaceFirst("\\..+$", ".webp"))
                .toFile();

        // Path => /root/image-UUID/preview/file.webp
        var createdCompressedFile = rootImageLocation
                .resolve(generatedFileId)
                .resolve(previewDir)
                .resolve(imageFileName.replaceFirst("\\..+$", ".webp"))
                .toFile();

        // when:
        imageService.persist(List.of(generatedFileId), sessionId);

        // then:
        assertTrue(createdOriginalFile.exists());
        assertTrue(createdCompressedFile.exists());
    }

    @Test
    void loadTempResourceTest() throws IOException {

        // given:
        var file = new MockMultipartFile(testImage.getName(), new FileInputStream(testImage));
        var sessionId = "fakeSessionId";

        // when:
        String type = ImageType.PREVIEW.getValue();
        String generatedFileName = imageService.storeToTemp(file, sessionId);
        byte[] tempResource = imageService.loadTempResource(generatedFileName, sessionId, type);

        // then:
        assertTrue(tempResource.length > 0);
    }

    @Test
    void loadResourceTest() throws IOException {

        // given:
        var file = new MockMultipartFile(
                testImage.getName().replaceFirst("\\..+$", ".webp"),
                new FileInputStream(testImage));
        var sessionId = "fakeSessionId";

        // when:
        String generatedFileName = imageService.storeToTemp(file, sessionId);
        imageService.persist(List.of(generatedFileName), sessionId);

        byte[] previewsResource = imageService.loadResource(generatedFileName, "preview");
        byte[] originalsResource = imageService.loadResource(generatedFileName, "original");

        // then:
        assertTrue(previewsResource.length > 0);
        assertTrue(originalsResource.length > 0);
    }

}