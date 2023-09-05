package baza.trainee.domain.model;

import baza.trainee.domain.enums.ContentType;
import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Data
@Document
public class Event {
    @Indexed
    @NotNull
    private String id;

    @Searchable
    @NotNull
    private ContentType contentType;

    @Searchable
    @NotNull
    @NotBlank
    private String title;

    @Searchable
    @NotNull
    private String content;

    @URL
    private String picture;

    @URL
    private String picturePreview;

    @NotNull
    private LocalDate begin;

    @NotNull
    private LocalDate end;

    private LocalDate created;

    @PastOrPresent
    private LocalDate updated;

    /**
     * An empty constructor (with no arguments).
     */
    public Event() {
        this.contentType = ContentType.EVENT;
        this.created = LocalDate.now();
    }
}
