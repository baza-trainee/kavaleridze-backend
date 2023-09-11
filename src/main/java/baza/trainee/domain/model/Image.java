package baza.trainee.domain.model;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@RedisHash("image")
@AllArgsConstructor
public class Image implements Serializable {

    private String id;

    private MultipartFile value;

}
