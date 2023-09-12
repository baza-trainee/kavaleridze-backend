package baza.trainee.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * MultipartFile wrapper for images.
 *
 * @author Evhen Malysh
 */
@Data
@Builder
@RedisHash
@AllArgsConstructor
public class Image implements Serializable {

    @Id
    private String id;
    private String name;
    private String contentType;
    private Long size;
    private byte[] data;

}
