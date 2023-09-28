package baza.trainee.controllers;

import baza.trainee.domain.enums.TypePicture;
import baza.trainee.exceptions.StorageFileNotFoundException;
import baza.trainee.services.ResourcePictureService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResourcePictureController.class)
class ResourcePictureControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ResourcePictureService resourcePictureService;


    @Test
    @SneakyThrows
    void getImage() {
        byte[] img = new UrlResource(Path.of(System.getProperty("user.dir"),
                "upload", "noImages.jpg").normalize().toAbsolutePath().toUri())
                .getContentAsByteArray();
        when(resourcePictureService.getPicture(any(TypePicture.class), anyString())).thenReturn(img);
        mockMvc.perform(get("/picture/{type}/{*file}" ,"original" ,"noImages.jpg")
                        .contentType(MediaType.IMAGE_JPEG_VALUE))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(img));
    }

    @SneakyThrows
    @Test
    void getImage_notStorageException() throws RuntimeException{
        when(resourcePictureService.getPicture(any(TypePicture.class), anyString()))
                .thenThrow(new StorageFileNotFoundException("Could not read file: "));
        mockMvc.perform(get("/picture/{type}/{*file}" , "original", "life.jpg"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}