package baza.trainee.domain.model;

import baza.trainee.domain.enums.BlockType;
import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@Document
public class ContentBlock {
    @Indexed
    private String id;

    @NotNull
    private Integer order;

    @NotNull
    private Integer columns;

    @NotNull
    private BlockType blockType;

    private String textContent;

    @URL
    private String pictureLink;

}
